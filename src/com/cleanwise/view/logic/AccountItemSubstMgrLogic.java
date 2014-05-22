
package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.cleanwise.view.forms.AccountItemSubstMgrForm;
import com.cleanwise.view.forms.AccountMgrDetailForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
/**
 * <code>ItemSubstMgrLogic</code> implements the logic needed to
 * manipulate account item substitutions.
 *
 * @author Yuriy Kupershmidt
 */
public class AccountItemSubstMgrLogic {
  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors init(HttpServletRequest request,
			  AccountItemSubstMgrForm pForm)
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
    pForm.setAccountId(accountId);
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
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Account Ejb access"));
	  return ae;
	}
    int accountCatalogId = 0;
    try {
      CatalogDataVector catalogDV = catalogInfEjb.getCatalogsCollectionByBusEntity(accountId);
      for(int ii=0; ii<catalogDV.size(); ii++) {
        CatalogData cD = (CatalogData) catalogDV.get(ii);
        if(RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.getCatalogStatusCd()) &&
           RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(cD.getCatalogTypeCd())) {
          if(accountCatalogId!=0) {
            ae.add("error",new ActionError("error.systemError","Account has more than one active catalog. Account id: "+accountId));
            return ae;
          }
          accountCatalogId = cD.getCatalogId();
        }
      }
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during account catalog request. Message: "+exc.getMessage()));
      return ae;
    }
    if(accountCatalogId==0) {
       ae.add("error",new ActionError("error.systemError","Account has no active catalog. Account id: "+accountId));
       return ae;
    }
    pForm.setAccountCatalogId(accountCatalogId);

    pForm.setSelectorBox(new String[0]);
    pForm.setSubstitutions(null);
    pForm.setUomConversionFactors(new HashMap());
    pForm.setSubstItems(null);
    return ae;
  }
  /**
  * Searches for item. Takes conditions form the form
  *
  */
  public static ActionErrors searchForItem (HttpServletRequest request,
			    AccountItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    int catalogId=pForm.getAccountCatalogId();
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
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Account Ejb access"));
	  return ae;
	}

    //Clear the search results
    pForm.setSubstitutions(null);

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
      itemIds = catalogInfEjb.searchCatalogProducts(catalogId,searchConditions,true);
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during item search request. Message: "+exc.getMessage()));
      return ae;
    }
    AccountItemSubstViewVector substitutions = null;
    try {
       substitutions = accountEjb.getAccountItemSubstitutions(itemIds,pForm.getAccountId(),!pForm.isSubstOnlyFlag());
    } catch (java.rmi.RemoteException exc) {
      ae.add("error",new ActionError("error.systemError","Error during substitutions request. Message: "+exc.getMessage()));
      return ae;
    }
    pForm.setSubstitutions(substitutions);
    HashMap uomConversionFactors = new HashMap(substitutions.size());
    for(int ii=0; ii<substitutions.size(); ii++) {
      AccountItemSubstView subst = (AccountItemSubstView) substitutions.get(ii);
      BigDecimal ff = subst.getUomConversionFactor();
      if(ff==null) ff = new BigDecimal(0);
      ff.setScale(2,BigDecimal.ROUND_HALF_UP);
      Integer indexI = new Integer(subst.getItemSubstitutionDefId());
      uomConversionFactors.put(indexI, ff.toString());
    }
    pForm.setUomConversionFactors(uomConversionFactors);
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors addFromLookup (HttpServletRequest request,
			    AccountItemSubstMgrForm pForm)
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
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Account Ejb access"));
	  return ae;
   }

    AccountItemSubstViewVector addObjects = (AccountItemSubstViewVector) session.getAttribute("lookupValue");
    session.removeAttribute("lookupValue");
    if(addObjects.size()>0) {
      AccountItemSubstView aisVw = (AccountItemSubstView) addObjects.get(0);
      int itemId = aisVw.getItemId();
      AccountItemSubstViewVector substitutions = pForm.getSubstitutions();
      for(int ii=0; ii<substitutions.size(); ii++) {
          
        AccountItemSubstView aisVw1 = (AccountItemSubstView) substitutions.get(ii);
        if(itemId==aisVw1.getItemId()){
          ItemSubstitutionDefData isdD = ItemSubstitutionDefData.createValue();
          isdD.setBusEntityId(pForm.getAccountId());
          isdD.setItemId(itemId);
          isdD.setSubstStatusCd(RefCodeNames.SUBST_STATUS_CD.ACTIVE);
          isdD.setSubstTypeCd(RefCodeNames.SUBST_TYPE_CD.ITEM_ACCOUNT);
          isdD.setUomConversionFactor(aisVw.getUomConversionFactor());
          isdD.setSubstItemId(aisVw.getSubstItemId());
          try {
            accountEjb.saveAccountItemSubstiutions(isdD,user);
          } catch (java.rmi.RemoteException exc) {
            ae.add("error",new ActionError("error.systemError","Can't create new substitution. Message: "+exc.getMessage()));
            return ae;
          }
          break;
        }
      }
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
  public static ActionErrors addSubstitutions (HttpServletRequest request,
			    AccountItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
      ae.add("error",new ActionError("No APIAccess."));
      return ae;
    }
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Account Ejb access"));
	  return ae;
	}
    //check selections
    String itemIdsS[] = pForm.getSelectorBox();
    if(itemIdsS.length==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No items selected"));
      return ae;
    }
    //Make substitutions vector
    ItemSubstitutionDefDataVector substitutions = new ItemSubstitutionDefDataVector();
    for(int ii=0; ii<itemIdsS.length; ii++) {
      try {
        String ss = itemIdsS[ii];
        int pos = ss.indexOf('#');
        String ss1 = "";
        if(pos>=0) {
          ss1 = ss.substring(0,pos);
        }else{
          ss1 = ss;
        }
        int itemId = Integer.parseInt(ss1);
        ItemSubstitutionDefData isdD = ItemSubstitutionDefData.createValue();
        isdD.setItemId(itemId);
        isdD.setBusEntityId(pForm.getAccountId());
        isdD.setSubstStatusCd(RefCodeNames.SUBST_STATUS_CD.ACTIVE);
        isdD.setSubstTypeCd(RefCodeNames.SUBST_TYPE_CD.ITEM_ACCOUNT);
        BigDecimal uomConvFactor = new BigDecimal(1);
        isdD.setUomConversionFactor(uomConvFactor);
        substitutions.add(isdD);
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.systemError","Wrong item id format: "+itemIdsS[ii]));
      }
    }
    //Add substIds
    ProductDataVector productDV = pForm.getSubstItems();
    if(productDV==null || productDV.size()==0) {
      ae.add("error",new ActionError("error.simpleGenericError","No substitute items selected"));
      return ae;
    }
    ItemSubstitutionDefDataVector substitutions1 = new ItemSubstitutionDefDataVector();
    for(int ii=0; ii<productDV.size(); ii++) {
      ProductData pD = (ProductData) productDV.get(ii);
      for(int jj=0; jj<substitutions.size(); jj++) {
        ItemSubstitutionDefData isdD = (ItemSubstitutionDefData) substitutions.get(jj);
        if(ii==0) {
          isdD.setSubstItemId(pD.getProductId());
          substitutions1.add(isdD);
        } else {
          ItemSubstitutionDefData isdD1 = ItemSubstitutionDefData.createValue();
          isdD1.setItemId(isdD.getItemId());
          isdD1.setBusEntityId(isdD.getBusEntityId());
          isdD1.setSubstStatusCd(isdD.getSubstStatusCd());
          isdD1.setSubstTypeCd(isdD.getSubstTypeCd());
          isdD1.setUomConversionFactor(isdD.getUomConversionFactor());
          isdD1.setSubstItemId(pD.getProductId());
          substitutions1.add(isdD1);
        }
      }
    }
    //Create substitutions
    try {
      for(int ii=0; ii<substitutions1.size(); ii++) {
        ItemSubstitutionDefData isdD = (ItemSubstitutionDefData) substitutions1.get(ii);
        accountEjb.saveAccountItemSubstiutions(isdD,user);
      }
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
  public static ActionErrors saveChanges (HttpServletRequest request,
			    AccountItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
      ae.add("error",new ActionError("No APIAccess."));
      return ae;
    }
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Account Ejb access"));
	  return ae;
	}
    //check changes
    HashMap uomConversionFactors = pForm.getUomConversionFactors();
    AccountItemSubstViewVector substitutions = pForm.getSubstitutions();
    ItemSubstitutionDefDataVector subtDefToSave = new ItemSubstitutionDefDataVector();
    if(uomConversionFactors!=null) {
      for(int ii=0; ii<substitutions.size(); ii++) {
         BigDecimal newUcf = null;
         AccountItemSubstView aisVw = (AccountItemSubstView) substitutions.get(ii);
         BigDecimal ucf = aisVw.getUomConversionFactor();
         if(ucf!=null) ucf.setScale(2,BigDecimal.ROUND_HALF_UP);
         int index = aisVw.getItemSubstitutionDefId();
         if(index==0) continue;
         Integer indexI = new Integer(index);
         if(!uomConversionFactors.containsKey(indexI)) {
           ae.add("error",new ActionError("error.simpleGenericError",
          "No consitancy. Old page was used?"));
           return ae;
         }
         String newUcfS = (String) uomConversionFactors.get(indexI);
         try {
           newUcf = new BigDecimal(newUcfS);
           newUcf.setScale(2,BigDecimal.ROUND_HALF_UP);
         }catch(Exception exc) {
           String mess = "Wrong Uom Conversion Factor format: "+ newUcfS;
           ae.add("error",new ActionError("error.simpleGenericError",mess));
           return ae;
         }
         if(newUcf.doubleValue()==0) {
            String mess = "Conversion Factor can't be empty or 0";
            ae.add("error",new ActionError("error.simpleGenericError",mess));
            return ae;
         }

         
         if(ucf==null || ucf.compareTo(newUcf)!=0) {
           aisVw.setUomConversionFactor(newUcf);
           ItemSubstitutionDefData isdD = ItemSubstitutionDefData.createValue();
           isdD.setItemId(aisVw.getItemId());
           isdD.setBusEntityId(aisVw.getAccountId());
           isdD.setSubstStatusCd(RefCodeNames.SUBST_STATUS_CD.ACTIVE);
           isdD.setSubstTypeCd(RefCodeNames.SUBST_TYPE_CD.ITEM_ACCOUNT);
           isdD.setUomConversionFactor(newUcf);
           isdD.setSubstItemId(aisVw.getSubstItemId());
           subtDefToSave.add(isdD);
         }
      }
    }
    try {
      for(int ii=0; ii<subtDefToSave.size(); ii++) {
        ItemSubstitutionDefData isdD = (ItemSubstitutionDefData) subtDefToSave.get(ii);
        accountEjb.saveAccountItemSubstiutions(isdD,user);
      }
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
			    AccountItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
      ae.add("error",new ActionError("No APIAccess."));
      return ae;
    }
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (APIServiceAccessException exc) {
	  ae.add("error",new ActionError("error.systemError","No Account Ejb access"));
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
        if(pos>0 && pos<ss.length()-1){
          Integer idI = new Integer(ss.substring(pos+1));
          contractItemSubstIds.add(idI);
        }
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.systemError","Wrong item id format: "+itemIdsS[ii]));
      }
    }
    //Remove substitutions
    try {
      accountEjb.removeAccountItemSubstiutions(contractItemSubstIds);
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
			    AccountItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
	String sortField = pForm.getSortField();
    AccountItemSubstViewVector substitutions = pForm.getSubstitutions();
    substitutions.sort(sortField);
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors selectAll (HttpServletRequest request,
			    AccountItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    AccountItemSubstViewVector substitutions = pForm.getSubstitutions();
    String[] selectorBox = new String[substitutions.size()];
    for(int ii=0; ii<substitutions.size(); ii++) {
      AccountItemSubstView cisVw = (AccountItemSubstView) substitutions.get(ii);
      String select = ""+cisVw.getItemId()+'#'+cisVw.getItemSubstitutionDefId();
      selectorBox[ii]=select;
    }
    pForm.setSelectorBox(selectorBox);
    return ae;
  }
  //---------------------------------------------------------------------------
  public static ActionErrors clearSelections (HttpServletRequest request,
			    AccountItemSubstMgrForm pForm)
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    pForm.setSelectorBox(new String[0]);
    return ae;
  }
}

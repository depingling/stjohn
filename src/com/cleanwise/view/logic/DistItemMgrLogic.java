package com.cleanwise.view.logic;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.DistMgrDetailForm;
import com.cleanwise.view.forms.ItemMgrSearchForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class DistItemMgrLogic {

    public static void init(HttpServletRequest request,
			    ItemMgrSearchForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
        DistMgrDetailForm distForm = initDistMgrDetailForm(request); 
        String distrIdS = distForm.getId();
        pForm.setDistributorId(distrIdS);

        int distId = Integer.parseInt(distrIdS);
        
        APIAccess factory = (APIAccess)
	    session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }

        ListService listServiceEjb = factory.getListServiceAPI();
        StoreData theStore = DistMgrLogic.getStoreForManagedDistributor(session,distId);
        
                
        pForm.setERPEnabled(false);

        
        //this is done on the jsp page now
        if (null == session.getAttribute("Item.dist.uom.vector")) {
	    ArrayList uomv = new ArrayList();
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_CS);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_EA);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_BX);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_DZ);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_PR);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_DR);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_TB);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_RL);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_DP);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_PL);
            uomv.add("GA"); //not in RefCodes
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_PK);
            uomv.add("PA"); //not in RefCodes
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_BD);
            uomv.add(RefCodeNames.ITEM_UOM_CD.UOM_OTHER);
	    session.setAttribute("Item.dist.uom.vector", uomv);

	}
    }

    public static ActionErrors search (HttpServletRequest request,
				       ItemMgrSearchForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
	ActionErrors ae = new ActionErrors();

	APIAccess factory = (APIAccess)session.getAttribute
	    (Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}

	CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	Distributor distributorEjb = factory.getDistributorAPI();
	Manufacturer manufacturerEjb = factory.getManufacturerAPI();

	//Clear the search results
	pForm.setListIds(new IdVector());
	pForm.setDistItems(new DistItemViewVector());

	//Create a set of filters
	Vector searchConditions = new Vector();
	//Category
	String category = pForm.getCategoryTempl();
	if(category!=null && category.trim().length()>0){
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.CATALOG_CATEGORY,
			       SearchCriteria.CONTAINS_IGNORE_CASE,category);
	    searchConditions.add(sc);
	}

	//Short Desc
	String shortDesc = pForm.getShortDescTempl();
	if(shortDesc!=null && shortDesc.trim().length()>0){
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
			       SearchCriteria.CONTAINS_IGNORE_CASE,shortDesc);
	    searchConditions.add(sc);
	}

	//Long Desc
	String longDesc = pForm.getLongDescTempl();
	if(longDesc!=null && longDesc.trim().length()>0){
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.PRODUCT_LONG_DESC,
			       SearchCriteria.CONTAINS_IGNORE_CASE,longDesc);
	    searchConditions.add(sc);
	}

	//Size
	String sizeDesc = pForm.getSizeTempl();
	if(sizeDesc!=null && sizeDesc.trim().length()>0){
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.SKU_SIZE,
			       SearchCriteria.CONTAINS_IGNORE_CASE,sizeDesc);
	    searchConditions.add(sc);
	}

	//Manufacturer
	String manuIdS = pForm.getManuId();
	if(manuIdS!=null && manuIdS.trim().length()>0){
	    int manuId = 0;
	    try{
		manuId = Integer.parseInt(manuIdS);
		ManufacturerData manuD =
		    manufacturerEjb.getManufacturer(manuId);
		pForm.setManuName(manuD.getBusEntity().getShortDesc());
	    }catch(NumberFormatException exc) {
		ae.add("error",new
		       ActionError("item.search.wrong_manufacturer"));
		return ae;
	    }catch(DataNotFoundException de) {
		return ae;
	    }

	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.MANUFACTURER_ID,
			       SearchCriteria.EXACT_MATCH,new Integer(manuId));
	    searchConditions.add(sc);
	}

	//distributor
    String skuType = pForm.getSkuType();
	String distrIdS = pForm.getDistributorId();
        int distrId = 0;
	if(distrIdS!=null && distrIdS.trim().length()>0){
	    try{
		distrId = Integer.parseInt(distrIdS);
		DistributorData distrD =
		    distributorEjb.getDistributor(distrId);
		pForm.setDistributorName(distrD.getBusEntity().getShortDesc());
	    }catch(NumberFormatException exc) {
		ae.add("error",new
		       ActionError("item.search.wrong_distributor"));
		return ae;
	    }catch(DataNotFoundException de) {
		return ae;
	    }
        
/*
When searching for an item, do not limit the results
to the current distributor.  This is how distributor
numbers are assigned to new items for a distributor.
durval, 1/5/2004.
 
*/
        if(skuType.equals("Distributor")) {
          SearchCriteria sc = new
          SearchCriteria(SearchCriteria.DISTRIBUTOR_ID,
                     SearchCriteria.EXACT_MATCH,new
                         Integer(distrId));
          searchConditions.add(sc);
        }


	}

	//sku
	String skuNumber = pForm.getSkuTempl();
	if(skuNumber!=null && skuNumber.trim().length()>0){
	    if(skuType.equals("System")) {
		SearchCriteria sc =
		    new SearchCriteria(SearchCriteria.CLW_SKU_NUMBER,
				       SearchCriteria.EXACT_MATCH,
				       skuNumber);
		searchConditions.add(sc);
	    }else if(skuType.equals("Manufacturer")){
		SearchCriteria sc =
		    new SearchCriteria(SearchCriteria.MANUFACTURER_SKU_NUMBER,
				       SearchCriteria.EXACT_MATCH,
				       skuNumber);
		searchConditions.add(sc);
	    }else if(skuType.equals("Distributor")){
		SearchCriteria sc = new SearchCriteria
		    (SearchCriteria.DISTRIBUTOR_SKU_NUMBER,
		     SearchCriteria.EXACT_MATCH,
		     skuNumber);
		searchConditions.add(sc);
	    }
	}

	//Find
	pForm.setSelectorBox(new String[0]);
	pForm.setResultSource("");
	IdVector itemIds = null;
	itemIds = catalogInfEjb.searchProducts(searchConditions);

	pForm.setListIds(itemIds);

	//Create page list
	DistItemViewVector distItems = distributorEjb.getDistItems(distrId, itemIds);

	pForm.setDistItems(distItems);
	return ae;
    }
    //--------------------------------------------------------------------------
    public static void sort(HttpServletRequest request,
			    ItemMgrSearchForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	DistItemViewVector distItemVwV = form.getDistItems();
	if (distItemVwV == null) {
	    return;
	}
	String sortField = request.getParameter("sortField");
	distItemVwV.sort(sortField);
    }
    //-------------------------------------------------------------------------
    public static ActionErrors updateItem
	(HttpServletRequest request,
	 ItemMgrSearchForm pForm )
    throws Exception
    {
	ActionErrors ae = new ActionErrors();

	String newDistUom, newDistPack, newDistSku, distId, itemId;
        BigDecimal newDistUomConvMult = null;
	itemId = request.getParameter("itemId");

	if (itemId == null ) {
		   String errorMess = "Item id missing.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
	distId = request.getParameter("distId");

	if (distId == null ) {
		   String errorMess = "Distributor id missing.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
	newDistSku = request.getParameter("newDistSku");
	if (newDistSku == null ) {
		   String errorMess = "Distributor SKU missing.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
	newDistPack = request.getParameter("newDistPack");
	if (newDistPack == null ) {
		   String errorMess = "Distributor pack missing.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
	newDistUom = request.getParameter("newDistUom");
	if (newDistUom == null ) {
		   String errorMess = "Distributor UOM missing.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
        
 String newDistUomConvMultStr = request.getParameter("newDistUomConvMult");
 if (newDistUomConvMultStr == null ) {
		   String errorMess = "Distributor UOM Conversion Multiplier missing.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
        try{
            newDistUomConvMult = new BigDecimal(newDistUomConvMultStr);
        }catch(Exception e){
		   String errorMess = "Distributor UOM Conversion Multiplier is not numeric.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
        }


	int int_itemId = 0;
	int int_distId = 0;
	try {
	    int_itemId = Integer.parseInt(itemId);
	    int_distId = Integer.parseInt(distId);
	}
	catch (Exception e) {
		   String errorMess = "Could not get the item or distributor id.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}

	String username = (String)
	    request.getSession().getAttribute("LoginUserName");

	APIAccess factory = (APIAccess)
	    request.getSession().getAttribute
	    (Constants.APIACCESS);
	if (factory==null) {
		   String errorMess = "NO EJB Access.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}

	try {
	    ItemInformation itemEjb = factory.getItemInformationAPI();
	    itemEjb.updateDistributorItemMapping
		(int_itemId, int_distId,
		 newDistUom, newDistPack, newDistSku, newDistUomConvMult, username );
	}
	catch (Exception e ) {
		   String errorMess = "Could not update the distributor information.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
        //Find object
        int ii = 0;
        DistItemViewVector distItemVwV = pForm.getDistItems();
        DistItemView diVw = null;
        for(; ii<distItemVwV.size(); ii++) {
          diVw = (DistItemView) distItemVwV.get(ii);
          if(int_itemId==diVw.getItemId()) {
            break;
          }
        }
        if(ii==distItemVwV.size()) {
          ae.add("error",new ActionError("error.simpleGenericError","Can't find requested item. Probably old page was used"));
          return ae;
        }
        diVw.setDistId(int_distId);
     //Update generic manufacturer information if provided
    String mfg1IdS = pForm.getMfg1Id();
    if(mfg1IdS!=null && mfg1IdS.trim().length()>0) {
        int mfg1Id = 0; 
        try {
          mfg1Id = Integer.parseInt(mfg1IdS);
        } catch (Exception exc){
          ae.add("error",new ActionError("error.simpleGenericError",
          "Wrong Mfg1 Id format: "+mfg1IdS));
          return ae;
        }
        Manufacturer manufacturerEjb = factory.getManufacturerAPI();
	try {
	   ManufacturerData mD = manufacturerEjb.getManufacturer(mfg1Id);
	}
	catch (Exception exc ) {
          ae.add("error",new ActionError("error.simpleGenericError",
          "Wrong Mfg1 Id: "+mfg1IdS+ " No manufacturer found"));
          return ae;
	}
        Distributor distributorEjb = factory.getDistributorAPI();
        diVw.setMfg1Id(mfg1Id);
        String mfg1ItemSku = pForm.getMfg1ItemSku();
        diVw.setMfg1ItemSku(mfg1ItemSku);
        distributorEjb.updateDitstItemMfgInfo(diVw, username);
    }
	return ae;
    }

    //-------------------------------------------------------------------------
    public static ActionErrors clearItem
	(HttpServletRequest request,
	 ItemMgrSearchForm pForm)
    throws Exception
    {
	ActionErrors ae = new ActionErrors();
	APIAccess factory = (APIAccess)
	    request.getSession().getAttribute
	    (Constants.APIACCESS);
	if (factory==null) {
		   String errorMess = "NO EJB Access.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
        String newDistUom, newDistPack, newDistSku, distId, itemId;
	itemId = request.getParameter("itemId");
	if (itemId == null ) {
		   String errorMess = "Item id missing.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
	distId = request.getParameter("distId");
	if (distId == null ) {
		   String errorMess = "Distributor id missing.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}

	int int_itemId = 0;
	int int_distId = 0;
	try {
	    int_itemId = Integer.parseInt(itemId);
	    int_distId = Integer.parseInt(distId);
	}
	catch (Exception e) {
		   String errorMess = "Could not get the item or distributor id.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}

	    //Reoove generic manufacturer info
        DistItemViewVector distItemVwV = pForm.getDistItems();
        DistItemView diVw = null;
        int ii = 0;
        for(; ii<distItemVwV.size(); ii++) {
          diVw = (DistItemView) distItemVwV.get(ii);
          if(int_itemId==diVw.getItemId()) {
             Distributor distributorEjb = factory.getDistributorAPI();
             distributorEjb.removeDitstItemMfgInfo(diVw);
             break;
          }
        }
        if(ii==distItemVwV.size()) {
          ae.add("error",new ActionError("error.simpleGenericError","Can't find requested item. Probably old page was used"));
          return ae;
        }
        
	//Remove distributor info
	try {
	    ItemInformation itemEjb = factory.getItemInformationAPI();
	    itemEjb.removeDistributorItemMapping
		(int_itemId, int_distId);
	}
	catch (Exception e ) {
		   String errorMess = "Could not remove the distributor information.";
     ae.add("error",new ActionError("error.simpleGenericError",errorMess));
	    return ae;
	}
	return ae;
    }
    
    private static DistMgrDetailForm initDistMgrDetailForm(HttpServletRequest request) 
    throws Exception
    {
      HttpSession session = request.getSession();
      DistMgrDetailForm distForm = 
            (DistMgrDetailForm) session.getAttribute("DIST_DETAIL_FORM");
      APIAccess factory = new APIAccess();
      Distributor distBean = factory.getDistributorAPI();
      BusEntityData distBeD = null;
      boolean newFormFl = false;
      if(distForm==null) {
        distForm = new DistMgrDetailForm();
        session.setAttribute("DIST_DETAIL_FORM",distForm);
        newFormFl = true;
      }
      String fieldValue = request.getParameter("searchField");
      int distId = 0;
      if(fieldValue!=null) {
        try {
          distId = Integer.parseInt(fieldValue);
        } catch (Exception exc) {
          String errorMess = "Wrond distributor id format: "+fieldValue;
          throw new Exception(errorMess);
        }
      }
      if(newFormFl && distId==0) {  
       String errorMess = "No distributor id found";
          throw new Exception(errorMess);
      }
      if(distId>0 && distForm.getIntId()!=distId) {
        distBeD = distBean.getDistributorBusEntity(distId);
        distForm.setId(distId);
        distForm.setDistNumber(distBeD.getErpNum());
        distForm.setName(distBeD.getShortDesc());
        distForm.setStatusCd(distBeD.getBusEntityStatusCd());
        distForm.setTypeCd(distBeD.getBusEntityTypeCd());
      }
    return distForm;
      
    }

}

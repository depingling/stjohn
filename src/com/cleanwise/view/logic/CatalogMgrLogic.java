
package com.cleanwise.view.logic;

import java.util.List;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.CatalogMgrSearchForm;
import com.cleanwise.view.forms.CatalogMgrDetailForm;
import com.cleanwise.view.forms.ItemMgrSearchForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.RefCodeNames;
import java.util.ArrayList;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>CatalogMgrLogic</code> implements the logic needed to
 * manipulate catalog records.
 *
 * @author durval
 */
public class CatalogMgrLogic {

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

	CatalogMgrSearchForm sForm = (CatalogMgrSearchForm)form;
	//sForm.setSearchType("catalogId");             
	//   searchAll(request, form);

        String v = "false";
        HttpSession session = request.getSession();
        session.setAttribute("validated", v);   
	return;
    }


    /**
     *Searches for the catalog using the specified criteria
     * @param request a <code>HttpServletRequest</code> value
     * @param the searchField
     * @param the searchType
     * @param containsFlag
     */
    static CatalogDataVector search(HttpServletRequest request,
    String searchField, String searchType)
    throws Exception {
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        
        Catalog catalogEjb   = factory.getCatalogAPI();
        CatalogInformation catalogInformationEjb   = factory.getCatalogInformationAPI();
        
        EntitySearchCriteria crit = new EntitySearchCriteria();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            ArrayList types = new ArrayList();
            types.add(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
            types.add(RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR);
            types.add(RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING);
            types.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
            crit.setSearchTypeCds(types);
        }
        
        CatalogDataVector catalogList = new CatalogDataVector();
        if(searchField != null && searchField.trim().length()>0 &&searchType != null) {
            if(searchType.equalsIgnoreCase("catalogId")) {
                int catalogId = Integer.parseInt(searchField);
                crit.setSearchId(searchField);
            } else {

                int matchType=
		    (searchType.equalsIgnoreCase("catalogNameContains"))?
		    EntitySearchCriteria.NAME_CONTAINS:
		    EntitySearchCriteria.NAME_STARTS_WITH;

                crit.setSearchNameType(matchType);
                crit.setSearchName(searchField);
            }
        }
        catalogList = catalogInformationEjb.getCatalogsByCrit(crit);
        return catalogList;
    }
    
    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void search(HttpServletRequest request,
			      ActionForm form)
	throws Exception {
	HttpSession session = request.getSession();
	CatalogMgrSearchForm sForm = (CatalogMgrSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}

	Catalog catalogEjb   = factory.getCatalogAPI();
	CatalogInformation catalogInformationEjb   = factory.getCatalogInformationAPI();

	String searchField = sForm.getSearchField();
	String searchType = sForm.getSearchType();
	boolean containsFlag = sForm.isContainsFlag();

	CatalogDataVector catalogList = search(request,searchField,searchType);
	sForm.setResultList(catalogList);
	sForm.setSearchField(searchField);
	sForm.setSearchType(searchType);
    }


    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void searchAll(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

	// Get a reference to the admin facade
	HttpSession session = request.getSession();
	CatalogMgrSearchForm sForm = (CatalogMgrSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}
        List catalogList;
	CatalogInformation catalogInformationEjb   = factory.getCatalogInformationAPI();
        EntitySearchCriteria crit = new EntitySearchCriteria();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            ArrayList types = new ArrayList();
            types.add(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
            types.add(RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR);
            types.add(RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING);
            types.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
            crit.setSearchTypeCds(types);
            catalogList = catalogInformationEjb.getCatalogsByCrit(crit);
        }else{
            catalogList = catalogInformationEjb.getCatalogCollection();
        }
	sForm.setResultList(catalogList);
	sForm.setSearchField("");

    }
  
    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
			    ActionForm form)
	throws Exception {
	
        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	CatalogMgrSearchForm sForm = (CatalogMgrSearchForm)form;
	if (sForm == null) {
	    return;
	}
	CatalogDataVector catalogs = (CatalogDataVector)sForm.getResultList();
	if (catalogs == null) {
	    return;
	}

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(catalogs, sortField);
    }


    public static void addCatalog(HttpServletRequest request,
				  ActionForm form)
	throws Exception {

	CatalogMgrDetailForm catalog = (CatalogMgrDetailForm) form;
	catalog = new CatalogMgrDetailForm();

	//System Catalog Id
	HttpSession session = request.getSession();
	session.setAttribute("CATALOG_DETAIL_FORM", catalog);
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}
	CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();

        ListService lsvc = factory.getListServiceAPI();

        // Set up the catalog status list.
	if (session.getAttribute("Catalog.status.vector") == null) {
	    RefCdDataVector statusv =
		lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
	    session.setAttribute("Catalog.status.vector", statusv);
	}

        // Set up the catalog type list.
	if (session.getAttribute("Catalog.type.vector") == null) {
	    RefCdDataVector type1v =
		lsvc.getRefCodesCollection("CATALOG_TYPE_CD");
	    session.setAttribute("Catalog.type1.vector", type1v);
	    // make 2nd list with no 'SYSTEM' entry
	    RefCdDataVector type2v = new RefCdDataVector();
	    Iterator typeI = type1v.iterator();
	    while (typeI.hasNext()) {
		RefCdData ref = (RefCdData)typeI.next();
		if (!ref.getValue().equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM)) {
		    type2v.add(ref);
		}
	    }
	    session.setAttribute("Catalog.type2.vector", type2v);
	}

	if (catalogInformationEjb.getSystemCatalogId() != 0) {
	    // System catalog already exists, can't allow that as pick
	    if (session.getAttribute("Catalog.type2.vector") == null) {
		RefCdDataVector type2v =
		    lsvc.getRefCodesCollection("CATALOG_TYPE_CD");
		
		Iterator typeI = type2v.iterator();
		while (typeI.hasNext()) {
		    RefCdData ref = (RefCdData)typeI.next();
		    if (!ref.getValue().equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM)) {
			type2v.add(ref);
		    }
		}
		session.setAttribute("Catalog.type2.vector", type2v);
		session.setAttribute("Catalog.type.vector", type2v);
		} else {
		    session.setAttribute("Catalog.type.vector", 
				 session.getAttribute("Catalog.type2.vector"));
		}
	} else {
	    // No system catalog yet, allow 'system' as pick
	    if (session.getAttribute("Catalog.type1.vector") == null) {
		RefCdDataVector type1v =
		    lsvc.getRefCodesCollection("CATALOG_TYPE_CD");
		session.setAttribute("Catalog.type1.vector", type1v);
		session.setAttribute("Catalog.type.vector", type1v);
	    } else {
		session.setAttribute("Catalog.type.vector", 
			       session.getAttribute("Catalog.type1.vector"));
	    }
	}
    }

    public static void editCatalog(
				   HttpServletRequest request,
				   ActionForm form,
				   String catalogId)
	throws Exception {
	String v = "false";
        HttpSession session = request.getSession();
        session.setAttribute("validated", v);   

	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}
	CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();
	CatalogData detail = catalogInformationEjb.getCatalog(Integer.parseInt(catalogId));

	CatalogMgrDetailForm detailForm = (CatalogMgrDetailForm) form;
	detailForm.setDetail(detail);
	int iCatalogId=detail.getCatalogId();

	BusEntityDataVector storeV = catalogInformationEjb.getStoreCollection(iCatalogId);
	if(storeV.size()>0) {
	    BusEntityData storeD=(BusEntityData) storeV.get(0);
	    detailForm.setStoreId(storeD.getBusEntityId());
	    detailForm.setStoreName(storeD.getShortDesc());
	} else  {
	    detailForm.setStoreId(0);
	    detailForm.setStoreName("");
	}

	CatalogData superCatalogD = catalogInformationEjb.getSuperCatalog(iCatalogId);
	int mId = (superCatalogD==null)?0:superCatalogD.getCatalogId();
	detailForm.setMasterCatalogId(mId);

	boolean mayDelete=true;
	BusEntityDataVector bed = catalogInformationEjb.getAccountCollection(iCatalogId);
	if(bed.size()>0) {
	    mayDelete=false;
	}
/*
	bed = catalogInformationEjb.getSiteCollection(iCatalogId);
	if(bed.size()>0) {
	    mayDelete=false;
	}
*/

	OrderGuideDataVector ogDV = catalogInformationEjb.getOrderGuideCollection(iCatalogId);
	if(ogDV.size()>0) {
	    mayDelete=false;
	}
	detailForm.setMayDelete(mayDelete);

        // Set up the catalog status list.
        ListService lsvc = factory.getListServiceAPI();
	if (session.getAttribute("Catalog.status.vector") == null) {
	    RefCdDataVector statusv =
		lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
	    session.setAttribute("Catalog.status.vector", statusv);
	}
    }



    public static ActionErrors saveCatalog(HttpServletRequest request,
					   ActionForm form)
	throws Exception
    {

	CatalogMgrDetailForm detailForm = (CatalogMgrDetailForm) form;
	ActionErrors ae = new ActionErrors();

	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}

	Catalog catalogEjb = factory.getCatalogAPI();
	CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	Store storeEjb = factory.getStoreAPI();

	CatalogData catalogD = detailForm.getDetail();
	int catalogId = catalogD.getCatalogId();

	//Check errors
	String type = catalogD.getCatalogTypeCd();
	if(type==null || type.trim().length()==0) {
	    ae.add("NoType",new ActionError("variable.empty.error","Catalog Type"));
	}

	int storeId = detailForm.getStoreId();

	if(storeId==0) {
	    ae.add("NoStore",new ActionError("variable.empty.error","Store Id"));
	} else {
	    try {
		StoreData store = storeEjb.getStore(storeId);
	    } catch(DataNotFoundException exc) {
		ae.add("StoreNotExists", new ActionError("catalog.new.wrong_store"));
	    }
	}
	String catalogName = catalogD.getShortDesc();

	if(catalogName==null ||catalogName.trim().length()==0 ){
	    ae.add("NoName",new ActionError("variable.empty.error","Catalog Name"));
	}


        CatalogInformation catalogInfoBean = factory.getCatalogInformationAPI();
 

        String status = catalogD.getCatalogStatusCd();
        CatalogData cat = null;    

        if(catalogD.getCatalogId() != 0){
          
          cat = catalogInfoBean.getCatalog(catalogD.getCatalogId());
	  String oldStatus = cat.getCatalogStatusCd();
          
	  if(status==null || status.trim().length()==0) {
	    ae.add("NoStatus",new ActionError("variable.empty.error","Catalog Status"));
	  }
        

          /****
          * If we want to activate the catalog we must do some checking.
          * If it is an account catalog, we must make sure the account it 
          * is attached to does not already have an active account catalog. 
          ****/
          if(oldStatus.equals(RefCodeNames.CATALOG_STATUS_CD.INACTIVE) && status.equals(RefCodeNames.CATALOG_STATUS_CD.ACTIVE) && type.equals(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT)){
            BusEntityDataVector assocAccountIds =   catalogInfoBean.getAccountCollection(cat.getCatalogId());   

            for(int k=0; k<assocAccountIds.size(); k++){     
              ActionErrors testError = new ActionErrors();
              BusEntityData bed = (BusEntityData)assocAccountIds.get(k);
              int id = bed.getBusEntityId();

              testError = testCatalogAssoc(id);
              if(testError.size()>0){
                ae.add("Catalog Config",
  	                new ActionError("catalog.active.error"));
              }
            }
          }
        }

	if(RefCodeNames.CATALOG_TYPE_CD.SYSTEM.equals(type)){
	    int systemCatalogId = catalogInfEjb.getSystemCatalogId();
	    if(systemCatalogId != 0 && systemCatalogId != catalogId) {
		ae.add("SystemCatalogExists", new ActionError("catalog.new.system_catalog_exists"));
	    }
	}

	if(RefCodeNames.CATALOG_TYPE_CD.STORE.equals(type)){
	    int masterCatalogId = catalogInfEjb.getStoreCatalogId(storeId);
	    if(masterCatalogId != 0 && masterCatalogId != catalogId) {
		ae.add("CatalogExists", new ActionError("catalog.new.master_catalog_exists"));
	    }
	}

	int parentCatalogId = detailForm.getFoundCatalogId();
	CatalogData parentCatalog=null;
	if(catalogId==0 && parentCatalogId!=0) {
	    try{
		parentCatalog = catalogInfEjb.getCatalog(parentCatalogId);
	    } catch (DataNotFoundException exc) {
		ae.add("ParentNotExists",new ActionError("catalog.new.parent_not_exist"));
	    }
	}
	if(ae.size()>0) {
	    return ae;
	}

	try {
	    if(catalogD.getCatalogId()==0) {
		if(parentCatalog!=null) {
		    catalogD = 
			catalogEjb.createCatalogFromCatalog(parentCatalogId,
							    catalogName, 
							    type,
							    status,
							    storeId,
							    user);
		} else {
		    CatalogRequestData catalogRD = 
			catalogEjb.addCatalog(catalogD,
					      detailForm.getStoreId(),user);
		    catalogId = catalogRD.getCatalogId();
		    catalogD = catalogInfEjb.getCatalog(catalogId);
		}
		detailForm.setDetail(catalogD);
	    } else {
		catalogEjb.updateCatalog(catalogD,user);
	    }
	} catch (DuplicateNameException de) {
	    ae.add("name", new ActionError("error.field.notUnique",
					   "Name"));
	}
	    
	return ae;
    }

    public static void deleteCatalog(
				     HttpServletRequest request,
				     ActionForm form)
	throws Exception {
	CatalogMgrDetailForm detailForm = (CatalogMgrDetailForm) form;

	int catalogId = detailForm.getDetail().getCatalogId();

	// Get a reference to the admin facade.
	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}

	Catalog catalogEjb = factory.getCatalogAPI();
	catalogEjb.removeCatalogCascade(detailForm.getDetail().getCatalogId(),user);
    }

    private static ActionErrors testCatalogAssoc(int pBusEntityId)
    throws Exception{   
        
      ActionErrors ae = new ActionErrors();

      APIAccess factory = new APIAccess();

      CatalogInformation catalogInfoBean = factory.getCatalogInformationAPI();
      CatalogDataVector catVec = new CatalogDataVector();
      CatalogData cat = null;
      Integer id = new Integer(pBusEntityId);

      catVec = catalogInfoBean.getCatalogsCollectionByBusEntity(pBusEntityId);

      //go through the list of other catalogs attached to the account, if any are active account catalogs, log an error 

      for(int j=0;j<catVec.size();j++){
       
        cat = (CatalogData)catVec.get(j);               
        String type = cat.getCatalogTypeCd();              
        String stat = cat.getCatalogStatusCd(); 
        
        if(stat.equals(RefCodeNames.CATALOG_STATUS_CD.ACTIVE) && type.equals("ACCOUNT")){
          Integer catId = new Integer(cat.getCatalogId());
          String s1 = catId.toString();
          String s2 = id.toString();
          ae.add("Catalog Config",
	         new ActionError("catalog.config.error", s1, s2)); 
        }
      } 

      return ae;
    }


    public static ActionErrors validateCatalog(HttpServletRequest request,
					   ActionForm form)
	throws Exception
    {

        
	CatalogMgrDetailForm detailForm = (CatalogMgrDetailForm) form;
	ActionErrors ae = new ActionErrors();

	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
        String v = "true";
        session.setAttribute("validated", v);
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}

               

	Catalog catalogEjb = factory.getCatalogAPI();
	CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	Store storeEjb = factory.getStoreAPI();

	CatalogData catalogD = detailForm.getDetail();
	int catalogId = catalogD.getCatalogId();
   
        CatalogInformation catInfo = factory.getCatalogInformationAPI();
        //Get the # of items in the Catalog
        int numItems = 0;
        
        CatalogStructureDataVector csd = catInfo.getCatalogStructuresCollection(catalogId);
        for(int i=0; i<csd.size();i++){
          CatalogStructureData csData = (CatalogStructureData)csd.get(i);
          String code = csData.getCatalogStructureCd();
          if(code.equals(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT)){

            numItems++;

          }
        }

        detailForm.setNumItems(numItems);

        //Get the # of active Contracts attached to the catalog.
        Contract con = factory.getContractAPI();
        ContractDescDataVector cdv = con.getContractDescsByCatalog(catalogId);
        int numActive = 0;
        for(int i=0; i<cdv.size();i++){

          ContractDescData cdd = (ContractDescData)cdv.get(i);
          String status = cdd.getStatus();
          if(status.equals(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE)){
            numActive++;
          }     

        }

        detailForm.setActiveContracts(numActive);
 
        //Get the # of distributors for the catalog
        int numDist = 0;
        CatalogAssocDataVector cad =         catInfo.getCatalogAssociationsCollection(catalogId);
        for(int i=0; i<cad.size();i++){
          CatalogAssocData caData = (CatalogAssocData)cad.get(i);
          String aCode = caData.getCatalogAssocCd();
          if(aCode.equals(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR)){

            numDist++;

          }
        }
        detailForm.setNumDistributors(numDist);

        //We can continue if the catalog has only one contract
        if(numActive == 1){


          //Items without a Distributor and/or Cost
          ProductDataVector noDist = new ProductDataVector();
        
          ItemMgrSearchForm imsForm = new ItemMgrSearchForm();
          
          imsForm.setCatalogId(catalogId);
          ItemMgrSearchLogic.init(request, imsForm);
          ae = ItemMgrSearchLogic.searchForItem(request,imsForm);
          ProductDataVector pd = imsForm.getProducts();
          for(int j=0; j<pd.size(); j++){

            ProductData product = (ProductData)pd.get(j);
  
            if(product.getCatalogDistributor() == null){

              noDist.add(product);
            }    
          }
          
          detailForm.setBadDist(noDist);     
        


          //Items with Missing Data
          ProductDataVector missingData = new ProductDataVector();

          for(int j=0; j<pd.size(); j++){

            ProductData product = (ProductData)pd.get(j);
            if(product.getManufacturer() == null){
              missingData.add(product);
            }else if(product.getManufacturerSku() == null){
              missingData.add(product); 
            }else if(product.getShortDesc() == null){
              missingData.add(product); 
            }else if(product.getEffDate() == null){
              missingData.add(product); 
            }else if(product.getLongDesc() == null){
              missingData.add(product); 
            }else if(product.getSize() == null){
              missingData.add(product); 
            }else if(product.getPack() == null){
              missingData.add(product); 
            }else if(product.getUom() == null){
              missingData.add(product); 
            }
          }



          detailForm.setMissingData(missingData);
          

        }
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
			    CatalogMgrDetailForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	ProductDataVector products = form.getBadDist();
	if (products == null) {
	    return;
	}
	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(products, sortField);
    }


}






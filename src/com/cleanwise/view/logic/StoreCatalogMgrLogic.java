package com.cleanwise.view.logic;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.StoreCatalogMgrForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.ArrayList;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>StoreCatalogMgrLogic</code> implements the logic needed to
 * manipulate catalog records.
 *
 * @author durval
 */
public class StoreCatalogMgrLogic {

  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors init(HttpServletRequest request,
                                  ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();

    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error", new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }
    int storeId = storeD.getStoreId();
    pForm.setStoreId(storeId);

    pForm.setContainsFlag(false);

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    initVectors(session, factory, pForm, storeId);

    return ae;
  }


  private static void initVectors(HttpSession session, APIAccess factory, StoreCatalogMgrForm pForm, int storeId)
    throws Exception {

    FreightTable freightTableEjb = factory.getFreightTableAPI();

    //if (session.getAttribute("FreightTable.vector") == null) {
      FreightTableDataVector freightTableList = freightTableEjb.getStoreFreightTables(storeId,
          RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
      session.setAttribute("FreightTable.vector", freightTableList);
   // }

    //if (session.getAttribute("Discount.FreightTable.vector") == null) {
        FreightTableDataVector discountFreightTableList = freightTableEjb.getStoreFreightTables(storeId, 
            RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT);
        session.setAttribute("Discount.FreightTable.vector", discountFreightTableList);
    //}

   ListService lsvc = factory.getListServiceAPI();
   if (session.getAttribute("Catalog.status.vector") == null) {
     RefCdDataVector statusv = lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
     session.setAttribute("Catalog.status.vector", statusv);
   }

   // Set up the catalog type list.
   if (session.getAttribute("Catalog.type.vector") == null) {
     RefCdDataVector type1v = lsvc.getRefCodesCollection("CATALOG_TYPE_CD");
     RefCdDataVector type2v = new RefCdDataVector();
     for (Iterator typeI = type1v.iterator(); typeI.hasNext(); ) {
       RefCdData ref = (RefCdData) typeI.next();
       String value = ref.getValue();
       if (!value.equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM) &&
           !value.equals(RefCodeNames.CATALOG_TYPE_CD.STORE) &&
           (pForm.getCleanwiseFl() ||
            value.equals(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT) ||
            value.equals(RefCodeNames.CATALOG_TYPE_CD.SHOPPING)
           )
         ) {
         type2v.add(ref);
       }
     }
     session.setAttribute("Catalog.type.vector", type2v);
   }
  }

  /**
   * <code>search</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors search(HttpServletRequest request,
                                    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    Catalog catalogEjb = factory.getCatalogAPI();
    CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();

    EntitySearchCriteria crit = new EntitySearchCriteria();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
    ArrayList types = new ArrayList();
    String typeFilter = pForm.getCatalogType();
    if (!Utility.isSet(typeFilter)) {
      types.add(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
      types.add(RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR);
      types.add(RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING);
      types.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
    } else {
      types.add(typeFilter);
    }
    crit.setSearchTypeCds(types);

    String searchField = pForm.getSearchField();
    String searchType = pForm.getSearchType();
    boolean showInactiveFl = pForm.getShowInactiveFlag();
    boolean containsFlag = pForm.isContainsFlag();

    CatalogDataVector catalogList = new CatalogDataVector();

    if (Utility.isSet(searchField)) {
      if (searchType.equalsIgnoreCase("catalogId")) {
    	  try{
    		  int catalogId = Integer.parseInt(searchField);
    	  } catch(Exception e){
    		  ae.add("error", new ActionError("error.simpleGenericError", "Invalid CatalogID"));
    		  return ae;
    	  }
        crit.setSearchId(searchField);
      } else {
        int matchType = (searchType.equalsIgnoreCase("catalogNameContains")) ?
                        crit.NAME_CONTAINS : crit.NAME_STARTS_WITH;
        crit.setSearchNameType(matchType);
        crit.setSearchName(searchField);
      }
    }
    if (!showInactiveFl) {
      LinkedList statusLL = new LinkedList();
      statusLL.add(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
      statusLL.add(RefCodeNames.CATALOG_STATUS_CD.LIMITED);
      crit.setSearchStatusCdList(statusLL);
    }

    //Set account
    IdVector accountIdV = new IdVector();
    AccountDataVector accountDV = pForm.getFilterAccounts();
    if (accountDV != null) {
      for (Iterator iter = accountDV.iterator(); iter.hasNext(); ) {
        AccountData aD = (AccountData) iter.next();
        int accountId = aD.getBusEntity().getBusEntityId();
        accountIdV.add(new Integer(accountId));
      }
    }
    crit.setAccountBusEntityIds(accountIdV);
    catalogList = catalogInformationEjb.getCatalogsByCrit(crit);

    pForm.setResultList(catalogList);
    pForm.setSearchField(searchField);
    // pForm.setSearchType(searchType);
    return ae;
  }

  public static ActionErrors setCatalogFilter(HttpServletRequest request,
                                              ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

    CatalogDataVector selectedCatalogDV = new CatalogDataVector();
    int[] selectedCatalogIds = pForm.getSelectedCatalogIds();
    List catalogs = pForm.getResultList();
    for (Iterator iter = catalogs.iterator(); iter.hasNext(); ) {
      CatalogData cD = (CatalogData) iter.next();
      int cId = cD.getCatalogId();
      for (int ii = 0; ii < selectedCatalogIds.length; ii++) {
        if (cId == selectedCatalogIds[ii]) {
          selectedCatalogDV.add(cD);
          break;
        }
      }
    }
    pForm.setSelectedCatalogs(selectedCatalogDV);
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
                          ActionForm form) throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
    if (pForm == null) {
      return;
    }
    CatalogDataVector catalogs = (CatalogDataVector) pForm.getResultList();
    if (catalogs == null) {
      return;
    }

    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(catalogs, sortField);
  }


  public static ActionErrors addCatalog(HttpServletRequest request,
                                        ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error", new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }
    int storeId = storeD.getStoreId();

    CatalogData catalogD = CatalogData.createValue();
    pForm.setCatalogDetail(catalogD);
    pForm.setFoundCatalogId(0);
    pForm.setFoundCatalogName("");

    OrderGuideDescDataVector orderGuideDescDV = new OrderGuideDescDataVector();
    OrderGuideDescData ogdD = OrderGuideDescData.createValue();
    ogdD.setOrderGuideName("");
    ogdD.setCatalogName("");
    ogdD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
    ogdD.setStatus("");
    orderGuideDescDV.add(ogdD);
    pForm.setOrderGuides(orderGuideDescDV);

    ContractData contractD = ContractData.createValue();
    contractD = ContractData.createValue();
    contractD.setCatalogId(0);
    contractD.setContractItemsOnlyInd(false);
    contractD.setContractStatusCd(catalogD.getCatalogStatusCd());
    contractD.setContractTypeCd("UNKNOWN");
    contractD.setEffDate(Constants.getCurrentDate());
    contractD.setFreightTableId(0);
    contractD.setDiscountTableId(0);
    contractD.setHidePricingInd(false);
    contractD.setLocaleCd(null);
    contractD.setRankWeight(0);
    contractD.setShortDesc(catalogD.getShortDesc());
    contractD.setRefContractNum("0");
	contractD.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
    pForm.setContractDetail(contractD);

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();

    ListService lsvc = factory.getListServiceAPI();

    // Set up the catalog status list.
    if (session.getAttribute("Catalog.status.vector") == null) {
      RefCdDataVector statusv =
        lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
      session.setAttribute("Catalog.status.vector", statusv);
    }

    return ae;
  }


    public static ActionErrors saveCatalog(HttpServletRequest request, ActionForm form)
            throws Exception {

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        String user = (String) session.getAttribute(Constants.USER_NAME);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        Store storeEjb = factory.getStoreAPI();
        PropertyService propEjb = factory.getPropertyServiceAPI();

        CatalogData catalogD = pForm.getCatalogDetail();
        int catalogId = catalogD.getCatalogId();

        //Check errors
        String type = catalogD.getCatalogTypeCd();
        if (type == null || type.trim().length() == 0) {
            ae.add("NoType", new ActionError("variable.empty.error", "Catalog Type"));
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        StoreData storeD = appUser.getUserStore();
        if (storeD == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No store info"));
            return ae;
        }

        int storeId = storeD.getStoreId();

        if (storeId == 0) {
            ae.add("NoStore", new ActionError("variable.empty.error", "Store Id"));
        } else {
            try {
                StoreData store = storeEjb.getStore(storeId);
            } catch (DataNotFoundException exc) {
                ae.add("StoreNotExists", new ActionError("catalog.new.wrong_store"));
            }
        }

        String catalogName = catalogD.getShortDesc();
        if (catalogName == null || catalogName.trim().length() == 0) {
            //String mess = "Order guide name shouldn't be longer 30 characters";
            ae.add("Name", new ActionError("variable.empty.error", "Catalog Name"));
        } else if (catalogName.length() > 30) {
            String mess = "Catalog name shouldn't be longer 30 characters";
            ae.add("LongName", new ActionError("error.simpleGenericError", mess));
        }

        OrderGuideDescDataVector orderGuideDescDV = pForm.getOrderGuides();

        //if store property is set
        String propVal;
        try{
        	propVal = propEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOT_REQD);
        }catch(Exception e){
        	propVal = null;
        }

        if (orderGuideDescDV == null && propVal!=null && propVal.equalsIgnoreCase("false")) {
        	//String mess = "No order guide name provided";
        	String mess = "Order Guide Name";
        	ae.add("NoOgName", new ActionError("variable.empty.error", mess));
        } else {
        	boolean ogFl = false;
        	int index = 0;
        	for (Iterator iter = orderGuideDescDV.iterator(); iter.hasNext(); ) {
        		index++;
        		OrderGuideDescData ogdD = (OrderGuideDescData) iter.next();
        		String orderGuideName = ogdD.getOrderGuideName();
        		if (Utility.isSet(orderGuideName)) {
        			ogFl = true;
        			if (orderGuideName.length() > 30) {
        				String mess = "Order guide name is longer 30 characters: " + orderGuideName;
        				ae.add("LongName" + index, new ActionError("error.simpleGenericError", mess));
        			}
        		}
        	}
        	if (!ogFl && propVal!=null && propVal.equalsIgnoreCase("false")) {
        		//String mess = "No order guide name provided";
        		String mess = "Order Guide Name";
        		ae.add("NoName", new ActionError("variable.empty.error", mess));
        	}
        }

        String status = catalogD.getCatalogStatusCd();
        if (!Utility.isSet(status)) {
            ae.add("NoStatus", new ActionError("variable.empty.error", "Catalog Status"));
        }

        CatalogInformation catalogInfoBean = factory.getCatalogInformationAPI();

        int parentCatalogId = pForm.getFoundCatalogId();
        CatalogData parentCatalog = null;

        if (catalogId == 0 && parentCatalogId != 0) {
            try {
                parentCatalog = catalogInfEjb.getCatalog(parentCatalogId);
            } catch (DataNotFoundException exc) {
                ae.add("ParentNotExists", new ActionError("catalog.new.parent_not_exist"));
            }
        }

        boolean copyContractF = false;

        if (catalogD.getCatalogId() == 0 &&
                !Utility.isSet(pForm.getCopyContractFl()) &&
                pForm.getFoundCatalogId() > 0) {
            ae.add("copyContractFl", new ActionError("variable.empty.error", "Clone Catalog Prices"));
        } else if (catalogD.getCatalogId() == 0 && pForm.getCopyContractFl().equals("copyContractYes")) {
            copyContractF = true;
        }

        if (ae.size() > 0) {
            return ae;
        }

        if (parentCatalogId == 0
                && RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(type)
                && pForm.getPopulateCatalogFl()) {
            parentCatalogId = catalogInfEjb.getStoreCatalogId(storeId);
        }
        //
        List catalogList = pForm.getResultList();
        if (catalogList == null) {
            catalogList = new LinkedList();
            pForm.setResultList(catalogList);
        }

        ContractData contractD = pForm.getContractDetail();
        String freightTableIdS = pForm.getFreightTableId();
        String discountTableIdS = pForm.getDiscountTableId();

        int freightTableId = 0;
        int discountTableId = 0;
        try {
            freightTableId = Integer.parseInt(freightTableIdS);
        } catch (Exception exc) {
        }
        try {
            discountTableId = Integer.parseInt(discountTableIdS);
        } catch (Exception exc) {
        }

        /////////////////////////////////////////////////////////////////////
        // Check of existence of the association
        // between the freight table and the distributor
        /////////////////////////////////////////////////////////////////////
        FreightTable freightTableEjb = factory.getFreightTableAPI();
        FreightTableData currFreightTable = freightTableEjb.getFreightTable(freightTableId);
        //if (currFreightTable != null) {
          //  if (currFreightTable.getDistributorId() > 0) {
                //ae.add("error", new ActionError("error.simpleGenericError",
                //    "Selected 'Freight/Handling Table' is already associated with the distributor"));
           // }
       // }
        if (ae.size() > 0) {
            return ae;
        }

        ///
        StringBuilder freightAndDiscountMessages = new StringBuilder();
        boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue());
        if (!isaMLAStore) {
            boolean isDiscountTableError = false;
            boolean isFreightTableError = false;
            if (discountTableId > 0) {
                FreightTableData frTable = freightTableEjb.getFreightTable(discountTableId, 
                    RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT);
                if (frTable != null) {
                    if (!(frTable.getDistributorId() > 0)) {
                        isDiscountTableError = true;
                    }
                }
            }
            if (freightTableId > 0) {
                FreightTableData frTable = freightTableEjb.getFreightTable(freightTableId, 
                    RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
                if (frTable != null) {
                    if (!(frTable.getDistributorId() > 0)) {
                        isFreightTableError = true;
                    }
                }
            }
            if (isDiscountTableError || isFreightTableError) {
                if (isDiscountTableError && isFreightTableError) {
                    freightAndDiscountMessages.append("Successfully saved. Warning no distributor associated with Discount Table [");
                    freightAndDiscountMessages.append(String.valueOf(discountTableId));
                    freightAndDiscountMessages.append("]. ");
                    freightAndDiscountMessages.append("Warning no distributor associated with Freight Table [");
                    freightAndDiscountMessages.append(String.valueOf(freightTableId));
                    freightAndDiscountMessages.append("]");
                } else {
                    if (isDiscountTableError) {
                        freightAndDiscountMessages.append("Successfully saved. Warning no distributor associated with Discount Table [");
                        freightAndDiscountMessages.append(String.valueOf(discountTableId));
                        freightAndDiscountMessages.append("]");
                    } else {
                        freightAndDiscountMessages.append("Successfully saved. Warning no distributor associated with Freight Table [");
                        freightAndDiscountMessages.append(String.valueOf(freightTableId));
                        freightAndDiscountMessages.append("]");
                    }
                }
            }
        }

        contractD.setFreightTableId(freightTableId);
        contractD.setDiscountTableId(discountTableId);

        try {

            CatalogContractView catConVw = null;
            boolean updatePriceFromLoader = false;
            if (pForm.getUpdatePriceFromLoader()!=null && pForm.getUpdatePriceFromLoader().equalsIgnoreCase("on")) {
            	updatePriceFromLoader = true;
            }
            catalogD.setShortDesc(catalogName.trim());

            if (catalogD.getCatalogId() == 0) {
                catConVw = catalogEjb.addCatalogContract(catalogD,
                        pForm.getContractDetail(), copyContractF,
                        orderGuideDescDV, storeId, parentCatalogId, user, updatePriceFromLoader);
                pForm.setFoundCatalogId(0);
                pForm.setFoundCatalogName("");

                pForm.setCatalogDetail(catConVw.getCatalog());
                pForm.setContractDetail(catConVw.getContract());
                catalogList.add(catConVw.getCatalog());

            } else {

                catConVw = catalogEjb.updateCatalogContract(catalogD,
                        pForm.getContractDetail(), orderGuideDescDV, storeId, user, updatePriceFromLoader);
                pForm.setFoundCatalogId(0);
                pForm.setFoundCatalogName("");

                pForm.setCatalogDetail(catConVw.getCatalog());
                pForm.setContractDetail(catConVw.getContract());
                for (int ii = 0; ii < catalogList.size(); ii++) {
                    CatalogData cD = (CatalogData) catalogList.get(ii);
                    if (cD.getCatalogId() == catalogId) {
                        catalogList.remove(ii);
                        catalogList.add(ii, catConVw.getCatalog());
                        break;
                    }
                }
            }

            if (catConVw != null) {
                orderGuideDescDV = new OrderGuideDescDataVector();
                OrderGuideDataVector ogDV = catConVw.getOrderGuides();
                if (ogDV != null) {
                    for (Iterator iter = ogDV.iterator(); iter.hasNext(); ) {
                        OrderGuideData ogD = (OrderGuideData) iter.next();
                        OrderGuideDescData ogdD = OrderGuideDescData.createValue();
                        orderGuideDescDV.add(ogdD);
                        ogdD.setOrderGuideId(ogD.getOrderGuideId());
                        ogdD.setCatalogId(catConVw.getCatalog().getCatalogId());
                        ogdD.setOrderGuideName(ogD.getShortDesc());
                        ogdD.setCatalogName(catConVw.getCatalog().getShortDesc());
                        ogdD.setOrderGuideTypeCd(ogD.getOrderGuideTypeCd());
                        ogdD.setStatus("");
                    }
                    pForm.setOrderGuides(orderGuideDescDV);
                }
            }
        } catch (DuplicateNameException de) {
            ae.add("name", new ActionError("error.field.notUnique",
                    "Catalog Name"));
            return ae;
        } catch (Exception exc) {
            String mess = exc.getMessage();
            if (mess == null) mess = "";
            int ind = mess.indexOf("^clw^");
            if (ind >= 0) {
                mess = mess.substring(ind + "^clw^".length());
                ind = mess.indexOf("^clw^");
                if (ind > 0) mess = mess.substring(0, ind);
                ae.add("error", new ActionError("error.simpleGenericError", mess));
                return ae;
            } else {
                throw exc;
            }
        }

        if (freightAndDiscountMessages.length() > 0) {
            ae.add("warning", new ActionError("error.simpleGenericError", 
                freightAndDiscountMessages.toString()));
        }

        return ae;
    }

  public static ActionErrors cloneCatalog(HttpServletRequest request,
                                          ActionForm form) throws Exception {

    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
    ActionErrors ae = new ActionErrors();

    HttpSession session = request.getSession();

    CatalogData catalogD = pForm.getCatalogDetail();
    int catalogId = catalogD.getCatalogId();
    pForm.setFoundCatalogId(catalogId);
    catalogD.setCatalogId(0);
    String name = catalogD.getShortDesc();
    if (name == null) name = "";
    name = "Clone of >>> " + name;
    catalogD.setShortDesc(name);
    catalogD.setCatalogTypeCd(null);
    ContractData contractD = pForm.getContractDetail();
    contractD.setContractId(0);
    return ae;
  }


  public static ActionErrors deleteCatalog(
    HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

    int catalogId = pForm.getCatalogDetail().getCatalogId();
    // Get a reference to the admin facade.
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    Catalog catalogEjb = factory.getCatalogAPI();
    catalogEjb.removeCatalogContract(catalogId, user);
    List catalogL = pForm.getResultList();
    for (Iterator iter = catalogL.iterator(); iter.hasNext(); ) {
      CatalogData cD = (CatalogData) iter.next();
      int cId = cD.getCatalogId();
      if (cId == catalogId) {
        iter.remove();
        break;
      }
    }
    return ae;
  }

  public static ActionErrors addOrderGuide(
    HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

    OrderGuideDescDataVector orderGuideDescDV = pForm.getOrderGuides();
    if (orderGuideDescDV == null) {
      orderGuideDescDV = new OrderGuideDescDataVector();
    }
    boolean addNewOgFl = true;
    int size = orderGuideDescDV.size();
    if (size > 0) {
      OrderGuideDescData ogdD = (OrderGuideDescData) orderGuideDescDV.get(size - 1);
      if (ogdD.getOrderGuideId() == 0) {
        addNewOgFl = false;
      }
    }

    if (addNewOgFl) {
      OrderGuideDescData ogdD = OrderGuideDescData.createValue();
      orderGuideDescDV.add(ogdD);
      CatalogData catalogD = pForm.getCatalogDetail();
      ogdD.setCatalogId(catalogD.getCatalogId());
      ogdD.setCatalogName(catalogD.getShortDesc());
      ogdD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
      ogdD.setStatus(catalogD.getCatalogStatusCd());
    }

    return ae;
  }

  private static ActionErrors testCatalogAssoc(int pBusEntityId) throws Exception {

    ActionErrors ae = new ActionErrors();

    APIAccess factory = new APIAccess();

    CatalogInformation catalogInfoBean = factory.getCatalogInformationAPI();
    CatalogDataVector catVec = new CatalogDataVector();
    CatalogData cat = null;
    Integer id = new Integer(pBusEntityId);

    catVec = catalogInfoBean.getCatalogsCollectionByBusEntity(pBusEntityId);

    //go through the list of other catalogs attached to the account, if any are active account catalogs, log an error

    for (int j = 0; j < catVec.size(); j++) {

      cat = (CatalogData) catVec.get(j);
      String type = cat.getCatalogTypeCd();
      String stat = cat.getCatalogStatusCd();

      if (stat.equals(RefCodeNames.CATALOG_STATUS_CD.ACTIVE) && type.equals("ACCOUNT")) {
        Integer catId = new Integer(cat.getCatalogId());
        String s1 = catId.toString();
        String s2 = id.toString();
        ae.add("Catalog Config",
               new ActionError("catalog.config.error", s1, s2));
      }
    }

    return ae;
  }

  public static ActionErrors editCatalog(HttpServletRequest request,
                                         ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    String v = "false";
    HttpSession session = request.getSession();
    session.setAttribute("validated", v);

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();
    OrderGuide orderGuideEjb = factory.getOrderGuideAPI();
    Contract contractEjb = factory.getContractAPI();

    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
    String catalogId = request.getParameter("id");

    CatalogData catalogD = catalogInformationEjb.getCatalog(Integer.parseInt(catalogId));
    pForm.setCatalogDetail(catalogD);
    pForm.setFoundCatalogId(0);
    pForm.setFoundCatalogName("");
    int iCatalogId = catalogD.getCatalogId();

    CatalogPropertyData catalogPropertyD = catalogInformationEjb.getCatalogProperty(iCatalogId);

    boolean updatePrice = Boolean.valueOf(catalogPropertyD.getValue()).booleanValue();
    if (updatePrice) {
    	pForm.setUpdatePriceFromLoader("on");
    } else {
    	pForm.setUpdatePriceFromLoader(null);
    }

    ContractDataVector contractDV = contractEjb.getContractsByCatalog(iCatalogId);
    int liveContQty = 0;
    ContractData contractD = null;
    for (Iterator iter = contractDV.iterator(); iter.hasNext(); ) {
      ContractData cD = (ContractData) iter.next();
      if (!RefCodeNames.CONTRACT_STATUS_CD.DELETED.equals(cD.getContractStatusCd())) {
        liveContQty++;
        contractD = cD;
      }
    }
    if (liveContQty > 1) {
      String errorMess = "Catalog has " + liveContQty + " related contracts ";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    if (liveContQty == 0) {
      contractD = ContractData.createValue();
      contractD.setCatalogId(iCatalogId);
      contractD.setContractItemsOnlyInd(false);
      contractD.setContractStatusCd(catalogD.getCatalogStatusCd());
      contractD.setContractTypeCd("UNKNOWN");
      contractD.setEffDate(Constants.getCurrentDate());
      contractD.setFreightTableId(0);
      contractD.setDiscountTableId(0);
      contractD.setHidePricingInd(false);
      contractD.setLocaleCd(null);
      contractD.setRankWeight(0);
      contractD.setShortDesc(catalogD.getShortDesc());
      contractD.setRefContractNum("0");
      contractD.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
    }
    pForm.setContractDetail(contractD);

    boolean mayDeleteFl =
      catalogInformationEjb.canRemoveCatalogContract(catalogD.getCatalogId(), contractD.getContractId());
    pForm.setMayDelete(mayDeleteFl);

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error", new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    if (storeD == null) {
      ae.add("error", new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    BusEntityDataVector storeV = catalogInformationEjb.getStoreCollection(iCatalogId);
    boolean storeMatchFl = false;
    for (int ii = 0; ii < storeV.size(); ii++) {
      BusEntityData sD = (BusEntityData) storeV.get(0);
      if (storeId == sD.getBusEntityId()) {
        storeMatchFl = true;
        break;
      }
    }
    if (!storeMatchFl) {
      ae.add("error", new ActionError("error.simpleGenericError", "No catalog found"));
      return ae;
    }

    CatalogData superCatalogD = catalogInformationEjb.getSuperCatalog(iCatalogId);
    int mId = (superCatalogD == null) ? 0 : superCatalogD.getCatalogId();
    pForm.setMasterCatalogId(mId);

    int freightTableId = contractD.getFreightTableId();
    if (freightTableId == 0) {
      pForm.setFreightTableId("");
      pForm.setCurrentFreightTable(null);
      pForm.setCurrentFreightTableCriteria(null);
    } else {
      pForm.setFreightTableId(String.valueOf(freightTableId));

      FreightTable freightTableEjb = factory.getFreightTableAPI();
      FreightTableData currentFreightTable = freightTableEjb.getFreightTable(freightTableId);
      pForm.setCurrentFreightTable(currentFreightTable);

      FreightTableCriteriaDescDataVector criterias =
        freightTableEjb.getAllFreightTableCriteriaDescs(freightTableId);
      pForm.setCurrentFreightTableCriteria(criterias);
    }

        int discountTableId = contractD.getDiscountTableId();
            if (discountTableId == 0) {
                pForm.setDiscountTableId("");
        } else {
            pForm.setDiscountTableId(String.valueOf(discountTableId));
        }

    OrderGuideDescDataVector orderGuideDescDV =
      orderGuideEjb.getCollectionByCatalog(catalogD.getCatalogId(), OrderGuide.TYPE_TEMPLATE);
    if (orderGuideDescDV == null) orderGuideDescDV = new OrderGuideDescDataVector();
    if (orderGuideDescDV.size() == 0) {
      OrderGuideDescData ogdD = OrderGuideDescData.createValue();
      ogdD.setCatalogId(catalogD.getCatalogId());
      ogdD.setOrderGuideName("");
      ogdD.setCatalogName(catalogD.getShortDesc());
      ogdD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
      ogdD.setStatus("");
      orderGuideDescDV.add(ogdD);
    }
    pForm.setOrderGuides(orderGuideDescDV);


    initVectors(session, factory, pForm, storeId);

    return ae;
  }


}



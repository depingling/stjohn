package com.cleanwise.view.logic;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;

/**
 *  <code>OrderGuidesMgrLogic</code> implements the logic needed to manipulate
 *  order guides.
 *
 *@author     veronika
 *@created    October 13, 2006
 */
public class Admin2SiteOrderGuideLogic {
  private static final String ADMIN2_SITE_DETAIL_MGR_FORM = "ADMIN2_SITE_DETAIL_MGR_FORM";
  private static final String ADMIN2_SITE_ORDER_GUIDE_FORM ="ADMIN2_SITE_ORDER_GUIDE_FORM";
  private static final String SITE_ORDER_GUIDE_FOUND_VECTOR = "Site.OrderGuides.found.vector";
//  private static final String SITE_ID = "Site.id" ;
  private static final String SITE_CATALOG = "storeSiteCatalog";

  private static ContractData getBestContractData(ContractDataVector contracts) {
    Iterator it = contracts.iterator();
    HashMap contractMap = new HashMap();
    while (it.hasNext()) {
      ContractData contract = (ContractData) it.next();
      String status = contract.getContractStatusCd();
      if (contractMap.containsKey(status)) {
        ContractDataVector c = (ContractDataVector) contractMap.get(status);
        c.add(contract);
      } else {
        ContractDataVector c = new ContractDataVector();
        c.add(contract);
        contractMap.put(status, c);
      }
    }
    ContractDataVector c = (ContractDataVector) contractMap.get(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
    int size;
    if (c == null) {
      size = 0;
    } else {
      size = c.size();
    }

    if (size == 1) {
      return (ContractData) c.get(0);
    } else if (size == 0) {
      c = (ContractDataVector) contractMap.get(RefCodeNames.CONTRACT_STATUS_CD.ROUTING);
      if (c == null) {
        size = 0;
      } else {
        size = c.size();
      }
      if (size == 1) {
        return (ContractData) c.get(0);
      } else if (size == 0) {
        c = (ContractDataVector) contractMap.get(RefCodeNames.CONTRACT_STATUS_CD.LIVE);
        if (c == null) {
          size = 0;
        } else {
          size = c.size();
        }
        if (size == 1) {
          return (ContractData) c.get(0);
        } else if (size == 0) {
          c = (ContractDataVector) contractMap.get(RefCodeNames.CONTRACT_STATUS_CD.INACTIVE);
          if (c == null) {
            size = 0;
          } else {
            size = c.size();
          }
          if (size == 1) {
            return (ContractData) c.get(0);
          }
        }
      }
    }
    return null;
  }


  /**
   *  <code>getDetail</code>, fetch the information describing the order
   *  guide.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void getDetail(HttpServletRequest request, ActionForm form) throws Exception {

    APIAccess factory = new APIAccess();
    Contract contractBean = factory.getContractAPI();

    Admin2SiteOrderGuideDetForm sForm = (Admin2SiteOrderGuideDetForm) form;

    String fieldValue = request.getParameter("searchField");
    int orderGuideId = 0;
    if (null == fieldValue) {
      OrderGuideData ogd = (OrderGuideData) sForm.getOrderGuideInfoData().getOrderGuideData();
      orderGuideId = ogd.getOrderGuideId();
      if (orderGuideId == 0) {
        return;
      }
      fetchOrderGuideData(orderGuideId, sForm);
    } else {
      Integer id = new Integer(fieldValue);
      orderGuideId = id.intValue();

      if (orderGuideId == 0) {
        return;
      }
      fetchOrderGuideData(orderGuideId, sForm);
    }
    // get site property "share order guides"
    if (sForm.getSiteId() > 0) {
      Site siteBean = factory.getSiteAPI();
      SiteData sd = siteBean.getSite(sForm.getSiteId(), 0, true);
      PropertyData pr = sd.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);

      if (pr != null && Utility.isTrue(pr.getValue())) {
        sForm.setShareBuyerOrderGuides(true);
      } else {
        sForm.setShareBuyerOrderGuides(false);
      }
    }

    OrderGuideData ogd = sForm.getOrderGuideInfoData().getOrderGuideData();
    sForm.setUsingCatalogsContract(false);
    int contractId = 0;

    try {
      ContractDataVector contracts = contractBean.getContractsByCatalog(ogd.getCatalogId());
      ContractData contract = getBestContractData(contracts);
      if (contract != null) {
        contractId = contract.getContractId();
        sForm.setUsingCatalogsContract(true);
      }
    } catch (Exception e) {
      //just proceed using list price
      e.printStackTrace();
    }

    if (contractId > 0) {
      String contractName = (sForm).getContractName();
      ArrayList contractItemList = new ArrayList();
      contractItemList = contractBean.getItems(contractId);
      OrderGuideItemDescDataVector newOrderGuideItemList = new OrderGuideItemDescDataVector();
      ArrayList oldOrderGuideItemList = sForm.getOrderGuideItemCollection();

      for (int i = 0; i < oldOrderGuideItemList.size(); i++) {
        OrderGuideItemDescData ogitem = (OrderGuideItemDescData) oldOrderGuideItemList.get(i);
        for (int j = 0; j < contractItemList.size(); j++) {
          ContractItemData citem = (ContractItemData) contractItemList.get(j);
          if (ogitem.getItemId() == citem.getItemId()) {
            if (null != citem.getAmount()) {
              ogitem.setPrice(citem.getAmount());
            }
            break;
          }
        }
        newOrderGuideItemList.add(ogitem);
      }
      sForm.setOrderGuideItemCollection(newOrderGuideItemList);
      OrderGuideInfoData newinfo = sForm.getOrderGuideInfoData();
      newinfo.setOrderGuideItems(newOrderGuideItemList);
      sForm.setOrderGuideInfoData(newinfo);

      sForm.setContractId(Integer.toString(contractId));
      sForm.setContractName(contractName);
    }

  }


  /**
   *  <code>init</code> method.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static ActionErrors init(HttpServletRequest request,
                                  ActionForm form) throws Exception {
    ActionErrors lErrors = new ActionErrors();
    HttpSession session = request.getSession();
    Admin2SiteOrderGuideDetForm sForm = (Admin2SiteOrderGuideDetForm) form;

    APIAccess factory = new APIAccess();
    OrderGuideDescDataVector dv = new OrderGuideDescDataVector();
    session.setAttribute(SITE_ORDER_GUIDE_FOUND_VECTOR, dv);

    Admin2SiteDetailMgrForm dForm = (Admin2SiteDetailMgrForm) session.getAttribute(ADMIN2_SITE_DETAIL_MGR_FORM);
    if (dForm == null ){
      String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.siteDetailFormIsNull", null);
      lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(errorMess));
      return lErrors;
    }
    SiteData siteDetail = dForm.getSiteData();
    int siteId = siteDetail.getSiteId();
    if (siteId <= 0) {
      String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.siteIdWasNotSpecified", null);
      lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(errorMess));
      return lErrors;
    }
    sForm.setSiteId(siteId);

    OrderGuide ogBean = factory.getOrderGuideAPI();
    dv = ogBean.getCollectionByBusEntityId(siteId, OrderGuide.TYPE_BUYER_AND_SITE);

    // add catalog into session object (for Locate Item within catalog)
    Site siteBean = factory.getSiteAPI();
    SiteData sd = siteBean.getSite(siteId, 0,true);
    int catalogId = sd.getSiteCatalogId();
    try {
      CatalogInformation cati = factory.getCatalogInformationAPI();
      CatalogData cd = cati.getCatalog(catalogId);
      CatalogDataVector catV = new CatalogDataVector();
      catV.add(cd);
      session.setAttribute(SITE_CATALOG, catV);
    } catch (Exception e) {
      lErrors.add(ActionErrors.GLOBAL_ERROR,
                  new ActionError("site.order_guide.no_catalog"));

    }
    session.setAttribute(SITE_ORDER_GUIDE_FOUND_VECTOR, dv);
    session.setAttribute(ADMIN2_SITE_ORDER_GUIDE_FORM, sForm);
    return lErrors;
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
    OrderGuideDescDataVector orderguides =
      (OrderGuideDescDataVector) session.getAttribute(SITE_ORDER_GUIDE_FOUND_VECTOR);
    if (orderguides == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(orderguides, sortField);
  }

  /**
   *  <code>sortItems</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void sortItems(HttpServletRequest request,
                               ActionForm form) throws Exception {

    Admin2SiteOrderGuideDetForm sForm = getOrderGuideDetailForm(request, form);
    if (sForm == null) {
      // not expecting this, but nothing to do if it is
      return;
    }
    ArrayList ogItemsAL = sForm.getOrderGuideItemCollection();
    if (ogItemsAL == null || ogItemsAL.size() == 0) {
      return;
    }

    OrderGuideItemDescDataVector orderguideItems = new OrderGuideItemDescDataVector();
    for (Iterator iter = ogItemsAL.iterator(); iter.hasNext(); ) {
      OrderGuideItemDescData ogidD = (OrderGuideItemDescData) iter.next();
      orderguideItems.add(ogidD);
    }

    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(orderguideItems, sortField);
    sForm.setOrderGuideItemCollection(orderguideItems);
    sForm.getOrderGuideInfoData().setOrderGuideItems(orderguideItems);
    // must do this so that quantity information retained
    request.setAttribute(ADMIN2_SITE_ORDER_GUIDE_FORM, sForm);
  }

  /**
   *  Description of the Method
   *
   *@param  request        Description of Parameter
   *@param  form           Description of Parameter
   *@exception  Exception  Description of Exception
   */
  public static void delete(HttpServletRequest request, ActionForm form) throws Exception {
    APIAccess factory = new APIAccess();
    OrderGuide ogBean = factory.getOrderGuideAPI();
    Admin2SiteOrderGuideDetForm sForm = getOrderGuideDetailForm(request, form);
    if (sForm == null) {
      return;
    }
    ogBean.removeOrderGuide(sForm.getOrderGuideInfoData().
                            getOrderGuideData().getOrderGuideId());
  }


  public static ActionErrors addOrderGuide(HttpServletRequest request, ActionForm form) throws Exception {
    ActionErrors lErrors = new ActionErrors();
    try {
      Admin2SiteOrderGuideDetForm sForm = new Admin2SiteOrderGuideDetForm();
      HttpSession session = request.getSession();
      sForm.setAction("");

      Admin2SiteDetailMgrForm dForm = (Admin2SiteDetailMgrForm) session.getAttribute(ADMIN2_SITE_DETAIL_MGR_FORM);
      if (dForm == null ){
        String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.siteDetailFormIsNull", null);
        lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(errorMess));
        return lErrors;
      }
      SiteData siteDetail = dForm.getSiteData();
      int siteId = siteDetail.getSiteId();
      if (siteId <= 0) {
        String errorMess = ClwI18nUtil.getMessage(request,"admin2.errors.siteIdWasNotSpecified", null);
        lErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(errorMess));
        return lErrors;
      }
      sForm.setSiteId(siteId);

      APIAccess factory = new APIAccess();
      Site siteBean = factory.getSiteAPI();
      SiteData sd = siteBean.getSite(sForm.getSiteId(), 0, true);

      // set catalog name (current catalog)
      int catalogId = sd.getSiteCatalogId();
      CatalogDataVector catV = (CatalogDataVector)session.getAttribute(SITE_CATALOG);
      CatalogData cd = null;
      if (catV != null){
        cd = (CatalogData)catV.get(0);
      } else {
        try {
          CatalogInformation cati = factory.getCatalogInformationAPI();
          cd = cati.getCatalog(catalogId);
          catV = new CatalogDataVector();
          catV.add(cd);
          session.setAttribute(SITE_CATALOG, catV);
        }
        catch (Exception e) {
          lErrors.add(ActionErrors.GLOBAL_ERROR,
                      new ActionError("site.order_guide.no_catalog"));

        }
      }
      if(cd != null) {
        sForm.setCatalogName(cd.getShortDesc());
      }

      OrderGuide ogBean = factory.getOrderGuideAPI();
      OrderGuideItemDescDataVector ov = ogBean.getItemsToAdd(catalogId, 0);
      OrderGuideInfoData ogd = OrderGuideInfoData.createValue();
      ogd.setOrderGuideItems(ov);
      OrderGuideData ogdata = OrderGuideData.createValue();
      ogdata.setCatalogId(cd.getCatalogId());
      ogdata.setBusEntityId(siteId);
      ogd.setOrderGuideData(ogdata);
      sForm.setOrderGuideInfoData(ogd);
      sForm.setOrderGuideItemCollection(ov);
      request.setAttribute(ADMIN2_SITE_ORDER_GUIDE_FORM, sForm);
      session.setAttribute(ADMIN2_SITE_ORDER_GUIDE_FORM, sForm);
    } catch (Exception e) {
      lErrors.add(ActionErrors.GLOBAL_ERROR,
                  new ActionError("site.order_guide.no_catalog"));

    }
    return lErrors;
  }

  /**
   *  Description of the Method
   *
   *@param  request        Description of Parameter
   *@param  form           Description of Parameter
   *@return                Description of the Returned Value
   *@exception  Exception  Description of Exception
   */
  public static ActionErrors checkData
    (HttpServletRequest request, ActionForm form) throws Exception {

    ActionErrors lErrors = new ActionErrors();
    Admin2SiteOrderGuideNewForm sForm = (Admin2SiteOrderGuideNewForm) form;

    String newName = sForm.getName();
    if (newName == null || newName.length() == 0) {
      lErrors.add("name", new ActionError("variable.empty.error", "Name"));
    }

    return lErrors;
  }


  /**
   *  Update the order guide description information.
   *
   *@param  request
   *@param  form
   *@return
   *@exception  Exception  Description of Exception
   */
  public static ActionErrors update
    (HttpServletRequest request, ActionForm form) throws Exception {
    ActionErrors lErrors = new ActionErrors();
    HttpSession session = request.getSession();
    Admin2SiteOrderGuideDetForm sForm = getOrderGuideDetailForm(request, form);
    OrderGuideData fogd = null;
    if (sForm != null) {
      fogd = sForm.getOrderGuideInfoData().getOrderGuideData();
      if (fogd != null && fogd.getShortDesc().length() == 0) {
        lErrors.add("name", new ActionError("variable.empty.error", "Name"));
      }
    }
    if (fogd == null || lErrors.size() > 0) {
      return lErrors;
    }
    String cuser = (String) session.getAttribute(Constants.USER_NAME);

    APIAccess factory = new APIAccess();
    OrderGuide ogBean = factory.getOrderGuideAPI();
    int id = fogd.getOrderGuideId();
    OrderGuideData ogd = null;
    boolean isCreate = (id == 0);

    if (isCreate) {
      ogd = OrderGuideData.createValue();
      ogd.setAddBy(cuser);
      ogd.setBusEntityId(sForm.getSiteId());
      ogd.setCatalogId(fogd.getCatalogId());
    } else {
      ogd = ogBean.getOrderGuide(id);
      ogd.setModBy(cuser);
    }

    ogd.setShortDesc(fogd.getShortDesc());
    ogd.setOrderGuideTypeCd(fogd.getOrderGuideTypeCd());

    ogd = ogBean.updateOrderGuide(ogd);

    if (isCreate) {
      fogd.setOrderGuideId(ogd.getOrderGuideId());
      lErrors = addItems(request, form);
    } else {
      lErrors = updateItems(request, form);
    }

    getDetail(request, form);
    return lErrors;
  }


  /**
   *  Description of the Method
   *
   *@param  request        Description of Parameter
   *@param  form           Description of Parameter
   *@return                Description of the Returned Value
   *@exception  Exception  Description of Exception
   */
  public static ActionErrors updateItems
    (HttpServletRequest request, ActionForm form) throws Exception {

    ActionErrors lErrors = new ActionErrors();

    HttpSession session = request.getSession();

    OrderGuideInfoData fogd = null;
    Admin2SiteOrderGuideDetForm sForm = getOrderGuideDetailForm(request, form);

    if (sForm != null) {
      fogd = sForm.getOrderGuideInfoData();
    }

    if (fogd == null || lErrors.size() > 0) {
      // Report the errors to allow for edits.
      return lErrors;
    }

    String cuser = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = new APIAccess();
    OrderGuide ogBean = factory.getOrderGuideAPI();

    // Get the updated quantities.
    ArrayList idv = sForm.getOrderGuideItemCollection();
    OrderGuideItemDescDataVector idvorig = fogd.getOrderGuideItems();
    Object[] idvA = sortById(idv);
    Object[] idvorigA = sortById(idvorig);
    for (int jj = 0, ii = 0; ii < idvA.length; ii++) {
      OrderGuideItemDescData newo = (OrderGuideItemDescData) idvA[ii];
      OrderGuideItemDescData oldo = (OrderGuideItemDescData) idvorigA[ii];
      if (newo.getQuantity() != oldo.getQuantity() &&
          newo.getOrderGuideStructureId() == oldo.getOrderGuideStructureId()
        ) {
        // The quantity has changed, issue an
        // update.
        oldo.setQuantity(newo.getQuantity());
        ogBean.updateQuantity(newo.getOrderGuideStructureId(), newo.getQuantity(), cuser);
      }
    }

    return lErrors;
  }

  private static Object[]
    sortById(List pOrderGuideItems) {
    if (pOrderGuideItems == null)return new OrderGuideItemDescData[0];
    Object[] pogiA = pOrderGuideItems.toArray();
    for (int ii = 0; ii < pogiA.length - 1; ii++) {
      boolean exitFl = true;
      for (int jj = 0; jj < pogiA.length - ii - 1; jj++) {
        OrderGuideItemDescData ogidD1 = (OrderGuideItemDescData) pogiA[jj];
        OrderGuideItemDescData ogidD2 = (OrderGuideItemDescData) pogiA[jj + 1];
        if (ogidD1.getOrderGuideStructureId() > ogidD2.getOrderGuideStructureId()) {
          pogiA[jj] = ogidD2;
          pogiA[jj + 1] = ogidD1;
          exitFl = false;
        }
      }
      if (exitFl)break;
    }
    return pogiA;
  }

  /**
   *  Description of the Method
   *
   *@param  request        Description of Parameter
   *@param  form           Description of Parameter
   *@return                Description of the Returned Value
   *@exception  Exception  Description of Exception
   */
  public static ActionErrors findItems
    (HttpServletRequest request, ActionForm form) throws Exception {

    ActionErrors lErrors = new ActionErrors();
    OrderGuideInfoData fogd = null;
    Admin2SiteOrderGuideDetForm sForm = getOrderGuideDetailForm(request, form);

    if (sForm != null) {
      fogd = sForm.getOrderGuideInfoData();
    }

    if (fogd == null || lErrors.size() > 0) {
      // Report the errors to allow for edits.
      return lErrors;
    }

    APIAccess factory = new APIAccess();
    Site siteBean = factory.getSiteAPI();
    SiteData sd = siteBean.getSite(sForm.getSiteId(), 0,true);
    int catalogId = sd.getSiteCatalogId();

    OrderGuide ogBean = factory.getOrderGuideAPI();
    OrderGuideItemDescDataVector ov = ogBean.getItemsToAdd(catalogId, fogd.getOrderGuideData().getOrderGuideId());

    fogd.setOrderGuideItems(ov);
    sForm.setOrderGuideInfoData(fogd);

    return lErrors;
  }


  /**
   *  Description of the Method
   *
   *@param  request        Description of Parameter
   *@param  form           Description of Parameter
   *@return                Description of the Returned Value
   *@exception  Exception  Description of Exception
   */
  public static ActionErrors removeItems
    (HttpServletRequest request, ActionForm form) throws Exception {

    ActionErrors lErrors = new ActionErrors();

    Admin2SiteOrderGuideDetForm sForm = getOrderGuideDetailForm(request, form);

    OrderGuideData fogd = null;

    if (sForm != null) {
      fogd = sForm.getOrderGuideInfoData().getOrderGuideData();
    }

    if (fogd == null || lErrors.size() > 0) {
      // Report the errors to allow for edits.
      return lErrors;
    }

    APIAccess factory = new APIAccess();
    OrderGuide ogBean = factory.getOrderGuideAPI();
    String[] itemstorm = sForm.getSelectItems();
    for (int i = 0; i < itemstorm.length; i++) {
      ogBean.removeItem(Integer.parseInt(itemstorm[i]));
    }

    getDetail(request, form);
    return lErrors;
  }


  private static Admin2SiteOrderGuideDetForm getOrderGuideDetailForm(HttpServletRequest request, ActionForm form) {
    if (form instanceof OrderGuidesMgrDetailForm) {
      return (Admin2SiteOrderGuideDetForm) form;
    }
    HttpSession session = request.getSession();
    return (Admin2SiteOrderGuideDetForm) session.getAttribute(ADMIN2_SITE_ORDER_GUIDE_FORM);
  }


  /**
   *  Add items to an order guide.
   *
   *@param  request
   *@param  form
   *@return
   *@exception  Exception
   */
  public static ActionErrors addItems
    (HttpServletRequest request, ActionForm form) throws Exception {

    ActionErrors lErrors = new ActionErrors();

    Admin2SiteOrderGuideDetForm sForm = getOrderGuideDetailForm(request, form);

    OrderGuideData fogd = null;
    if (sForm != null) {
      fogd = sForm.getOrderGuideInfoData().getOrderGuideData();
    }

    if (fogd == null || lErrors.size() > 0) {
      // Report the errors to allow for edits.
      return lErrors;
    }

    APIAccess factory = new APIAccess();
    OrderGuide ogBean = factory.getOrderGuideAPI();
    String[] itemstoadd = sForm.getSelectItems();
    int selectedItemsCount = itemstoadd.length;
    if (selectedItemsCount > 0) {
      int ogid = fogd.getOrderGuideId();
      ArrayList idv = sForm.getOrderGuideItemCollection();
      int itemsCount = idv.size();
      Object[] idvA = sortById(idv);
      for (int ii = 0; ii < idvA.length; ii++) {
        OrderGuideItemDescData newo = (OrderGuideItemDescData) idvA[ii];
        if (selectedItemsCount != itemsCount) {
          // check that item is selected for add
          boolean selected = false;
          for (int i = 0; i < itemstoadd.length; i++) {
            if (Integer.parseInt(itemstoadd[i]) == newo.getItemId()) {
              selected = true;
              break;
            }
          }
          if (!selected)continue;
        }
        ogBean.addItem(ogid, newo.getItemId(), newo.getQuantity(),
                       SessionTool.getCategoryToCostCenterView(request.getSession(), 0, fogd.getCatalogId() ));
      }
    }

    return lErrors;
  }

  public static ActionErrors addNewItems
    (HttpServletRequest request, ActionForm form) throws Exception {

    ActionErrors lErrors = new ActionErrors();

    Admin2SiteOrderGuideDetForm sForm = getOrderGuideDetailForm(request, form);

    OrderGuideData fogd = null;
    if (sForm != null) {
      fogd = sForm.getOrderGuideInfoData().getOrderGuideData();
    }

    if (fogd == null || lErrors.size() > 0) {
      // Report the errors to allow for edits.
      return lErrors;
    }

    APIAccess factory = new APIAccess();
    OrderGuide ogBean = factory.getOrderGuideAPI();
    String[] itemstoadd = sForm.getSelectItems();
    int selectedItemsCount = itemstoadd.length;
    if (selectedItemsCount > 0) {
      int ogid = fogd.getOrderGuideId();
      for (int i = 0; i < selectedItemsCount; i++) {
        int itemId = Integer.parseInt(itemstoadd[i]);
        ogBean.addItem(ogid, itemId, 0,
                       SessionTool.getCategoryToCostCenterView(request.getSession(), 0, fogd.getCatalogId() ));
      }
    }

    return lErrors;
  }


  /**
   *  Get the information to describe an order guide.
   *
   *@param  pOrderGuideId
   *@param  pForm
   *@param  pReq
   *@exception  Exception
   */
  private static void fetchOrderGuideData
    (int pOrderGuideId, Admin2SiteOrderGuideDetForm sForm) throws Exception {

    APIAccess factory = new APIAccess();
    OrderGuide ogBean = factory.getOrderGuideAPI();

    OrderGuideInfoData newinfo = ogBean.getOrderGuideInfo(pOrderGuideId);

    sForm.setOrderGuideInfoData(newinfo);
    OrderGuideItemDescDataVector ogitems = newinfo.getOrderGuideItems();
    // Set the starting order to be by SKU (req from paula and andy)
    DisplayListSort.sort(ogitems, "cwSKU");
    sForm.setOrderGuideItemCollection(ogitems);

    // Get the catalog name.
    CatalogInformation cati = factory.getCatalogInformationAPI();
    CatalogData cd = cati.getCatalog(newinfo.getOrderGuideData().getCatalogId());
    sForm.setCatalogName(cd.getShortDesc());

  }


}



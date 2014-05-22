
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

/**
 *  <code>OrderGuidesMgrLogic</code> implements the logic needed to manipulate
 *  order guides.
 *
 *@author     durval
 *@created    August 16, 2001
 */
public class OrderGuidesMgrLogic {

    /**
     *  Gets the the order guide templates.
     *
     *@param  request
     *@param  form
     *@exception  Exception
     */
    public static void getAll(HttpServletRequest request,
                              ActionForm form)
        throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        OrderGuide ogi = factory.getOrderGuideAPI();

        OrderGuideDescDataVector dv = ogi.getCollectionByType
            (RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
        session.setAttribute("OrderGuides.found.vector", dv);
        session.setAttribute("OrderGuides.found.total",
                             String.valueOf(dv.size()));
    }


    /**
     *  <code>getDetail</code>, fetch the information describing the order
     *  guide.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getDetail(HttpServletRequest request,
                                 ActionForm form)
        throws Exception {
        getDetailByContract(request, form);
    }


    private static ContractData getBestContractData(ContractDataVector contracts){

        Iterator it = contracts.iterator();
        HashMap contractMap = new HashMap();
        while(it.hasNext()){
            ContractData contract = (ContractData) it.next();
            String status = contract.getContractStatusCd();
            if(contractMap.containsKey(status)){
                ContractDataVector c = (ContractDataVector) contractMap.get(status);
                c.add(contract);
            }else{
                ContractDataVector c = new ContractDataVector();
                c.add(contract);
                contractMap.put(status, c);
            }
        }
        ContractDataVector c = (ContractDataVector) contractMap.get(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
        int size;
        if(c==null){
            size = 0;
        }else{
            size = c.size();
        }

        if(size == 1){
            return (ContractData) c.get(0);
        }else if(size == 0){
            c = (ContractDataVector) contractMap.get(RefCodeNames.CONTRACT_STATUS_CD.ROUTING);
            if(c==null){
                size = 0;
            }else{
                size = c.size();
            }
            if(size == 1){
                return (ContractData) c.get(0);
            }else if(size == 0){
                c = (ContractDataVector) contractMap.get(RefCodeNames.CONTRACT_STATUS_CD.LIVE);
                if(c==null){
                    size = 0;
                }else{
                    size = c.size();
                }
                if(size == 1){
                    return (ContractData) c.get(0);
                }else if(size == 0){
                    c = (ContractDataVector) contractMap.get(RefCodeNames.CONTRACT_STATUS_CD.INACTIVE);
                    if(c==null){
                        size = 0;
                    }else{
                        size = c.size();
                    }
                    if(size == 1){
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
    public static void getDetailByContract(HttpServletRequest request,ActionForm form)throws Exception {

        APIAccess factory = new APIAccess();
        Contract   contractBean = factory.getContractAPI();

        OrderGuidesMgrDetailForm sForm = (OrderGuidesMgrDetailForm)form;

        String fieldValue = request.getParameter("searchField");

        int orderGuideId = 0;
        if (null == fieldValue) {
            OrderGuideData ogd = (OrderGuideData)sForm.getOrderGuideInfoData().getOrderGuideData();
            orderGuideId = ogd.getOrderGuideId();
            if(orderGuideId == 0){
                return;
            }
            fetchOrderGuideData(orderGuideId,sForm);
        }
        else {
            Integer id = new Integer(fieldValue);
            orderGuideId = id.intValue();
            if(orderGuideId == 0){
                return;
            }
            fetchOrderGuideData(orderGuideId,sForm);
        }

        OrderGuideData ogd = sForm.getOrderGuideInfoData().getOrderGuideData();
        sForm.setUsingCatalogsContract(false);
        int contractId = 0;
        if("viewbycontract".equals(sForm.getViewMode())) {
           contractId = Integer.parseInt(sForm.getContractId());
        }else if("viewbylist".equals(sForm.getViewMode())) {
            contractId = 0;
        }else{
            try{
                ContractDataVector contracts = contractBean.getContractsByCatalog(ogd.getCatalogId());
                ContractData contract = getBestContractData(contracts);
                if(contract != null){
                    contractId = contract.getContractId();
                    sForm.setUsingCatalogsContract(true);
                }
            }catch(Exception e){
                //just proceed using list price
                e.printStackTrace();
            }
        }


        if(contractId > 0){
            String contractName = (sForm).getContractName();
            ArrayList contractItemList = new ArrayList();
            contractItemList  = contractBean.getItems(contractId);
            OrderGuideItemDescDataVector newOrderGuideItemList = new OrderGuideItemDescDataVector();
            ArrayList oldOrderGuideItemList = sForm.getOrderGuideItemCollection();

            for (int i = 0; i < oldOrderGuideItemList.size(); i++) {
                OrderGuideItemDescData ogitem = (OrderGuideItemDescData)oldOrderGuideItemList.get(i);
                for(int j = 0 ; j < contractItemList.size(); j++) {
                    ContractItemData citem = (ContractItemData) contractItemList.get(j);
                    if(ogitem.getItemId() == citem.getItemId()) {
                        if(null != citem.getAmount()) {
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

            //sForm.setViewMode("viewbycontract");
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
    public static void init(HttpServletRequest request,
                            ActionForm form)
        throws Exception {
        return;
    }



    /**
     *  <code>search</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void search
        (HttpServletRequest request, ActionForm form)
        throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        OrderGuidesMgrDetailForm sForm = (OrderGuidesMgrDetailForm) form;

        APIAccess factory = new APIAccess();
        OrderGuideDescDataVector dv =
            new OrderGuideDescDataVector();
        session.setAttribute("OrderGuides.found.vector", dv);

        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        OrderGuide ogBean = factory.getOrderGuideAPI();

        if (fieldSearchType.equals("id")) {
            Integer id = new Integer(fieldValue);
            OrderGuideDescData od = ogBean.getOrderGuideDesc
                (id.intValue());
            if (null != od) {
                dv.add(od);
            }
        }
        else {

            dv = null;
            int orderGuideType = OrderGuide.TYPE_ADMIN_RELATED;
	        if (fieldSearchType.equals("nameBegins")) {
                dv = ogBean.getCollectionByCatalogName(fieldValue,
                     OrderGuide.BEGINS_WITH_IGNORE_CASE, orderGuideType);
            }
            else { // nameContains
                dv = ogBean.getCollectionByCatalogName(fieldValue,
                     OrderGuide.CONTAINS_IGNORE_CASE, orderGuideType);
            }
        }

        session.setAttribute("OrderGuides.found.vector", dv);
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
	OrderGuideDescDataVector orderguides =
	    (OrderGuideDescDataVector)session.getAttribute("OrderGuides.found.vector");
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
				 ActionForm form)
	throws Exception {

        OrderGuidesMgrDetailForm sForm = getOrderGuideDetailForm(request, form);
	if (sForm == null) {
	    // not expecting this, but nothing to do if it is
	    return;
	}
	ArrayList ogItemsAL = sForm.getOrderGuideItemCollection();
    if(ogItemsAL==null || ogItemsAL.size()==0) {
      return;
    }

	OrderGuideItemDescDataVector orderguideItems = new OrderGuideItemDescDataVector();
    for(Iterator iter=ogItemsAL.iterator();iter.hasNext();) {
      OrderGuideItemDescData ogidD = (OrderGuideItemDescData) iter.next();
      orderguideItems.add(ogidD);
    }

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(orderguideItems, sortField);
    sForm.setOrderGuideItemCollection(orderguideItems);
	// must do this so that quantity information retained
	//request.setAttribute("ORDER_GUIDES_DETAIL_FORM", sForm);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void delete
        (HttpServletRequest request, ActionForm form) throws Exception {
        APIAccess factory = new APIAccess();
        OrderGuide ogBean = factory.getOrderGuideAPI();
        OrderGuidesMgrDetailForm sForm = (OrderGuidesMgrDetailForm) form;
        if (sForm == null) {
            return;
        }
        ogBean.removeOrderGuide(sForm.getOrderGuideInfoData().
                                getOrderGuideData().getOrderGuideId());
    }


    /**
     *  Adds a feature to the OrderGuide attribute of the OrderGuidesMgrLogic
     *  class
     *
     *@param  request        The feature to be added to the OrderGuide attribute
     *@param  form           The feature to be added to the OrderGuide attribute
     *@exception  Exception  Description of Exception
     */
    public static void addOrderGuide
        (HttpServletRequest request, ActionForm form) throws Exception {

        OrderGuidesMgrNewForm sForm = new OrderGuidesMgrNewForm();
        sForm.setTypeCd
            (RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
        sForm.setAction("");
        request.setAttribute("ORDER_GUIDES_NEW_FORM", sForm);

        return;
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
        OrderGuidesMgrNewForm sForm = (OrderGuidesMgrNewForm) form;

        String newName = sForm.getName();
        if (newName == null || newName.length() == 0) {
            lErrors.add("name", new ActionError("variable.empty.error",
                                                "Name"));
        }

        return lErrors;
    }


    /**
     *  Create an Order Guide fom the form information.
     *
     *@param  request
     *@param  form
     *@exception  Exception
     */
    public static void create
        (HttpServletRequest request, ActionForm form)
        throws Exception {

        HttpSession session = request.getSession();
        OrderGuidesMgrNewForm sForm = (OrderGuidesMgrNewForm) form;
        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        APIAccess factory = new APIAccess();
        OrderGuide ogi = factory.getOrderGuideAPI();
        String fromType = sForm.getCreateFrom();
        String userName = (String)session.getAttribute(Constants.USER_NAME);
        OrderGuidesMgrDetailForm ogDetailForm =
            new OrderGuidesMgrDetailForm();

        if (fromType.equals("Catalog")) {
            OrderGuideData ogd = OrderGuideData.createValue();
            ogd.setCatalogId(Integer.parseInt(sForm.getParentCatalogId()));
            ogd.setOrderGuideTypeCd
                (sForm.getNewOrderGuideType());
            ogd.setShortDesc(sForm.getName());
            ogd.setAddBy(cuser);
            OrderGuideInfoData ogid = ogi.createFromCatalog(ogd);
            ogDetailForm.setOrderGuideInfoData(ogid);
        }
        else if (fromType.equals("Contract")) {
            OrderGuideData ogd = OrderGuideData.createValue();
            ogd.setOrderGuideTypeCd
                (sForm.getNewOrderGuideType());
            ogd.setShortDesc(sForm.getName());
            ogd.setAddBy(cuser);
            int contractId = Integer.parseInt(sForm.getParentContractId());
            OrderGuideInfoData ogid = ogi.createFromContract(ogd, contractId);

            ogDetailForm.setOrderGuideInfoData(ogid);
        }
        else if (fromType.equals("OrderGuide")) {
            OrderGuideData ogd = OrderGuideData.createValue();
            int seedogid = Integer.parseInt(sForm.getParentOrderGuideId());
            ogd.setShortDesc(sForm.getName());
            ogd.setAddBy(cuser);
            ogd.setOrderGuideTypeCd
                (sForm.getNewOrderGuideType());
            ogd.setOrderGuideId(seedogid);

            OrderGuideInfoData ogid = ogi.createFromOrderGuide(ogd, userName);
            ogDetailForm.setOrderGuideInfoData(ogid);
        }
        request.setAttribute("ORDER_GUIDES_DETAIL_FORM", ogDetailForm);
        session.setAttribute("ORDER_GUIDES_DETAIL_FORM", ogDetailForm);
        getDetailByContract(request, ogDetailForm);
        return;
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
        (HttpServletRequest request, ActionForm form)
        throws Exception {
        ActionErrors lErrors = new ActionErrors();

        HttpSession session = request.getSession();
        OrderGuidesMgrDetailForm sForm = getOrderGuideDetailForm(request, form);

        OrderGuideData fogd = null;


        if (sForm != null) {
            fogd = sForm.getOrderGuideInfoData().getOrderGuideData();
            // Verify the form information submitted.
            if (fogd.getShortDesc().length() == 0) {
                lErrors.add("name",
                            new ActionError("variable.empty.error",
                                            "Name"));
            }
        }

        if (fogd == null || lErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lErrors;
        }

        APIAccess factory = new APIAccess();
        OrderGuide ogBean = factory.getOrderGuideAPI();
        int id = fogd.getOrderGuideId();
        OrderGuideData ogd = ogBean.getOrderGuide(id);

        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        ogd.setShortDesc(fogd.getShortDesc());
        ogd.setModBy(cuser);
        ogBean.updateOrderGuide(ogd);

        //fetchOrderGuideData(ogd.getOrderGuideId(), request);
        //fetchOrderGuideDataByViewMode(ogd.getOrderGuideId(), sForm.getViewMode(), request, sForm);

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
        (HttpServletRequest request, ActionForm form)
        throws Exception {

        ActionErrors lErrors = new ActionErrors();

        HttpSession session = request.getSession();

        OrderGuideInfoData fogd = null;
        OrderGuidesMgrDetailForm sForm = getOrderGuideDetailForm(request, form);
        //OrderGuidesMgrDetailForm reqForm =
//            (OrderGuidesMgrDetailForm)
  //          request.getAttribute("ORDER_GUIDES_DETAIL_FORM");

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
        for (int jj = 0, ii=0; ii < idvA.length; ii++) {
            OrderGuideItemDescData newo =
                (OrderGuideItemDescData) idvA[ii];
            OrderGuideItemDescData oldo =
                (OrderGuideItemDescData) idvorigA[ii];
            if (newo.getQuantity() != oldo.getQuantity() &&
                newo.getOrderGuideStructureId() ==
                oldo.getOrderGuideStructureId()
                ) {
                // The quantity has changed, issue an
                // update.
                oldo.setQuantity(newo.getQuantity());
                ogBean.updateQuantity(newo.getOrderGuideStructureId(),
                                      newo.getQuantity(), cuser);
            }
        }

        //fetchOrderGuideData(id, request);
        //fetchOrderGuideDataByViewMode(id, sForm.getViewMode(), request,sForm);
        getDetail(request, form);
        return lErrors;
    }

    private static Object[]
                       sortById(List pOrderGuideItems) {
      if(pOrderGuideItems==null) return new OrderGuideItemDescData[0];
      Object[] pogiA = pOrderGuideItems.toArray();
      for(int ii=0; ii<pogiA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<pogiA.length-ii-1; jj++) {
          OrderGuideItemDescData ogidD1 = (OrderGuideItemDescData) pogiA[jj];
          OrderGuideItemDescData ogidD2 = (OrderGuideItemDescData) pogiA[jj+1];
          if(ogidD1.getOrderGuideStructureId()>ogidD2.getOrderGuideStructureId()) {
            pogiA[jj] = ogidD2;
            pogiA[jj+1] = ogidD1;
            exitFl = false;
          }
        }
        if(exitFl) break;
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
        (HttpServletRequest request, ActionForm form)
        throws Exception {

        ActionErrors lErrors = new ActionErrors();
        OrderGuideInfoData fogd = null;
        OrderGuidesMgrDetailForm sForm = getOrderGuideDetailForm(request, form);

        if (sForm != null) {
            fogd = sForm.getOrderGuideInfoData();
        }

        if (fogd == null || lErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lErrors;
        }

        APIAccess factory = new APIAccess();
        OrderGuide ogBean = factory.getOrderGuideAPI();
        int id = fogd.getOrderGuideData().getOrderGuideId();

        OrderGuideInfoData ogid = ogBean.getCatalogItems(id);
        sForm.setOrderGuideInfoData(ogid);
        //request.setAttribute("ORDER_GUIDES_DETAIL_FORM", sForm);

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
        (HttpServletRequest request, ActionForm form)
        throws Exception {

        ActionErrors lErrors = new ActionErrors();

        OrderGuidesMgrDetailForm sForm = getOrderGuideDetailForm(request, form);

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


    private static OrderGuidesMgrDetailForm getOrderGuideDetailForm(HttpServletRequest request, ActionForm form){
        if(form instanceof OrderGuidesMgrDetailForm){
            return (OrderGuidesMgrDetailForm) form;
        }
        HttpSession session = request.getSession();
        return (OrderGuidesMgrDetailForm)session.getAttribute("ORDER_GUIDES_DETAIL_FORM");
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
        (HttpServletRequest request, ActionForm form)
        throws Exception {

        ActionErrors lErrors = new ActionErrors();

        OrderGuidesMgrDetailForm sForm = getOrderGuideDetailForm(request, form);

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
        int id = fogd.getOrderGuideId();
        for (int i = 0; i < itemstoadd.length; i++) {
            ogBean.addItem(id, Integer.parseInt(itemstoadd[i]), 0,
                SessionTool.getCategoryToCostCenterView(request.getSession(), 0, fogd.getCatalogId()));
        }

        //fetchOrderGuideData(id, request);
        //fetchOrderGuideDataByViewMode(id, sForm.getViewMode(), request, sForm);
        getDetail(request, form);
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
        (int pOrderGuideId, OrderGuidesMgrDetailForm sForm)
        throws Exception {

        APIAccess factory = new APIAccess();
        OrderGuide ogBean = factory.getOrderGuideAPI();

        OrderGuideInfoData newinfo =
            ogBean.getOrderGuideInfo(pOrderGuideId);


        sForm.setOrderGuideInfoData(newinfo);
        OrderGuideItemDescDataVector ogitems = newinfo.getOrderGuideItems();
        // Set the starting order to be by SKU (req from paula and andy)
        DisplayListSort.sort(ogitems, "cwSKU");
        sForm.setOrderGuideItemCollection(ogitems);

        // Get the catalog name.
        CatalogInformation cati = factory.getCatalogInformationAPI();
        CatalogData cd = cati.getCatalog
            (newinfo.getOrderGuideData().getCatalogId());
        sForm.setCatalogName(cd.getShortDesc());


    }



}



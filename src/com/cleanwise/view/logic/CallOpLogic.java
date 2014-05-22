
package com.cleanwise.view.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Troubleshooter;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.CallData;
import com.cleanwise.service.api.value.CallDescData;
import com.cleanwise.service.api.value.CallDescDataVector;
import com.cleanwise.service.api.value.CallPropertyData;
import com.cleanwise.service.api.value.CallPropertyDataVector;
import com.cleanwise.service.api.value.CallSearchCriteriaData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.forms.CallOpDetailForm;
import com.cleanwise.view.forms.CallOpNoteDetailForm;
import com.cleanwise.view.forms.CallOpSearchForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;

/**
 * <code>CallOpLogic</code> implements the logic needed to
 * manipulate call records.
 *
 * @author Liang
 */
public class CallOpLogic {

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

        initConstantList(request);
        initUserList(request, form, "search");
	//searchAll(request, form);
	return;
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
	CallOpSearchForm sForm = (CallOpSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}


	Troubleshooter troubleshooterEjb   = factory.getTroubleshooterAPI();

        CallSearchCriteriaData searchCriteria = CallSearchCriteriaData.createValue();

        if( sForm.getAccountName().trim().length() > 0 ) {
            searchCriteria.setAccountName(sForm.getAccountName().trim());
        }
        String accountIdList = sForm.getAccountIdList();
        if(Utility.isSet(accountIdList)) {
         accountIdList = accountIdList.trim();
         StringTokenizer tok = new StringTokenizer(accountIdList,",");
         IdVector accountIdV = new IdVector();
         while(tok.hasMoreTokens()){
           String aIdS =tok.nextToken().trim();
           try{
             int accountId = Integer.parseInt(aIdS);
             accountIdV.add(new Integer(accountId));
           } catch(Exception exc) {}
         }
         if(accountIdV.size()>0) {
           searchCriteria.setAccountIdVector(accountIdV);
         }
        }
        if( sForm.getSiteName().trim().length() > 0 ) {
            searchCriteria.setSiteName(sForm.getSiteName().trim());
        }
        if( sForm.getContactName().trim().length() > 0 ) {
            searchCriteria.setContactName(sForm.getContactName().trim());
        }
        if( sForm.getContactPhone().trim().length() > 0 ) {
            searchCriteria.setContactPhone(sForm.getContactPhone().trim());
        }
        if( sForm.getContactEmail().trim().length() > 0 ) {
            searchCriteria.setContactEmail(sForm.getContactEmail().trim());
        }
        if( sForm.getProductName().trim().length() > 0 ) {
            searchCriteria.setProductName(sForm.getProductName().trim());
        }
        if( sForm.getCustomerField1().trim().length() > 0 ) {
            searchCriteria.setCustomerField1(sForm.getCustomerField1().trim());
        }
        if( sForm.getSiteZipCode().trim().length() > 0 ) {
            searchCriteria.setSiteZipCode(sForm.getSiteZipCode().trim());
        }
        if( sForm.getErpOrderNum().trim().length() > 0 ) {
            searchCriteria.setErpOrderNum(sForm.getErpOrderNum().trim());
        }
        if( sForm.getCustPONum().trim().length() > 0 ) {
            searchCriteria.setCustPONum(sForm.getCustPONum().trim());
        }
        if( sForm.getErpPONum().trim().length() > 0 ) {
            searchCriteria.setErpPONum(sForm.getErpPONum().trim());
        }
        if( sForm.getWebOrderConfirmationNum().trim().length() > 0 ) {
            searchCriteria.setWebOrderConfirmationNum(sForm.getWebOrderConfirmationNum().trim());
        }
        if( ! "".equals(sForm.getCallTypeCd()) ) {
            searchCriteria.setCallTypeCd(sForm.getCallTypeCd());
        }
        if( ! "".equals(sForm.getCallSeverityCd()) ) {
            searchCriteria.setCallSeverityCd(sForm.getCallSeverityCd());
        }
        if( ! "".equals(sForm.getOpenedById()) ) {
            searchCriteria.setOpenedById(sForm.getOpenedById());
        }
        if( ! "".equals(sForm.getAssignedToId()) ) {
            searchCriteria.setAssignedToId(sForm.getAssignedToId());
        }
        if( ! "".equals(sForm.getCallStatusCd()) ) {
            searchCriteria.setCallStatusCd(sForm.getCallStatusCd());
        }
        if( sForm.getOrderDateRangeBegin().trim().length() > 0 ) {
            searchCriteria.setOrderDateRangeBegin(sForm.getOrderDateRangeBegin().trim());
        }
        if( sForm.getOrderDateRangeEnd().trim().length() > 0 ) {
            searchCriteria.setOrderDateRangeEnd(sForm.getOrderDateRangeEnd().trim());
        }
        if( sForm.getSiteData().trim().length() > 0 ) {
            searchCriteria.setSiteData(sForm.getSiteData().trim());
        }

        CallDescDataVector callDesc = new CallDescDataVector();
        callDesc = troubleshooterEjb.getCallDescCollection(searchCriteria);

	sForm.setResultList(callDesc);
    }

    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void searchMyCalls(HttpServletRequest request,
			      ActionForm form)
	throws Exception {

	HttpSession session = request.getSession();
	CallOpSearchForm sForm = (CallOpSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}


	Troubleshooter troubleshooterEjb   = factory.getTroubleshooterAPI();

        CallSearchCriteriaData searchCriteria = CallSearchCriteriaData.createValue();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");

        Integer id = new Integer(appUser.getUser().getUserId());
        String userId = id.toString();


        searchCriteria.setAssignedToId(userId);
        searchCriteria.setCallStatusCd(RefCodeNames.CALL_STATUS_CD.OPEN);

        CallDescDataVector callDesc = new CallDescDataVector();
        callDesc = troubleshooterEjb.getCallDescCollection(searchCriteria);

	sForm.setResultList(callDesc);
    }


    /**
     * <code>searchAll</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void searchAll(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

	HttpSession session = request.getSession();
	CallOpSearchForm sForm = (CallOpSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}

	Troubleshooter troubleshooterEjb   = factory.getTroubleshooterAPI();

        CallSearchCriteriaData searchCriteria = CallSearchCriteriaData.createValue();
        String userId = (String)session.getAttribute(Constants.USER_ID);
        sForm.setOpenedById(userId);
        sForm.setCallStatusCd(RefCodeNames.CALL_STATUS_CD.OPEN);

        searchCriteria.setOpenedById(userId);
        searchCriteria.setCallStatusCd(RefCodeNames.CALL_STATUS_CD.OPEN);
        CallDescDataVector callDesc = new CallDescDataVector();
        callDesc = troubleshooterEjb.getCallDescCollection(searchCriteria);

	sForm.setResultList(callDesc);
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
	CallOpSearchForm sForm = (CallOpSearchForm)form;
	if (sForm == null) {
	    return;
	}
	CallDescDataVector calls =
	    (CallDescDataVector)sForm.getResultList();
	if (calls == null) {
	    return;
	}

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(calls, sortField);

    }


    /**
     *  <code>sortItems</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    /*
    public static void sortItems(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

        CallOpDetailForm sForm =
            (CallOpDetailForm)
            session.getAttribute("ORDER_OP_DETAIL_FORM");
	if (sForm == null) {
	    // not expecting this, but nothing to do if it is
	    return;
	}

	CallItemStatusDescDataVector callItemStatusDescList =
	   (CallItemStatusDescDataVector)sForm.getCallItemStatusDescList();

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(callItemStatusDescList, sortField);
    }
     */


    /**
     *  <code>sortItemDetails</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    /*
    public static void sortItemDetails(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

        CallOpItemDetailForm sForm =
            (CallOpItemDetailForm)
            session.getAttribute("ORDER_OP_ITEM_DETAIL_FORM");
	if (sForm == null) {
	    // not expecting this, but nothing to do if it is
	    return;
	}

	CallItemDetailDataVector callItemDetailList =
	   (CallItemDetailDataVector)sForm.getCallItemDetailList();

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(callItemDetailList, sortField);
    }
     */



    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */
    public static void initConstantList(HttpServletRequest request)
	throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        ListService listServiceEjb = factory.getListServiceAPI();

        if (null == session.getAttribute("Call.status.vector")) {
	    RefCdDataVector statusv =
		listServiceEjb.getRefCodesCollection("CALL_STATUS_CD");
	    session.setAttribute("Call.status.vector", statusv);
	}

        if (null == session.getAttribute("Call.type.vector")) {
	    RefCdDataVector typev =
		listServiceEjb.getRefCodesCollection("CALL_TYPE_CD");
	    session.setAttribute("Call.type.vector", typev);
	}

        if (null == session.getAttribute("Call.severity.vector")) {
	    RefCdDataVector severityv =
		listServiceEjb.getRefCodesCollection("CALL_SEVERITY_CD");
	    session.setAttribute("Call.severity.vector", severityv);
	}

    }


    /**
     * <code>initUserList</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void initUserList(HttpServletRequest request,
			    ActionForm form, String formType)
	throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        User userEjb = factory.getUserAPI();
        UserDataVector customerServiceUserList = userEjb.getUsersCollectionByType(
                                    RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE);
        UserDataVector crcManagerUserList = userEjb.getUsersCollectionByType(
                                    RefCodeNames.USER_TYPE_CD.CRC_MANAGER);
        customerServiceUserList.addAll(crcManagerUserList);

        UserDataVector activeCrcs = new UserDataVector();

        for(int k=0; k<customerServiceUserList.size();k++){
            UserData ud = (UserData)customerServiceUserList.get(k);
            String status = ud.getUserStatusCd();
            if(status.equals(RefCodeNames.USER_STATUS_CD.ACTIVE)){
              activeCrcs.add(ud);
            }
        }
        if ("search".equals(formType)) {
            CallOpSearchForm sForm = (CallOpSearchForm)form;
            sForm.setCustomerServiceUserList(activeCrcs);
        }
        else if ("detail".equals(formType)) {
            CallOpDetailForm dForm = (CallOpDetailForm)form;
            dForm.setCustomerServiceUserList(activeCrcs);
        }
    }


    /**
     * <code>getCallDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param callId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void getCallDetail(
				    HttpServletRequest request,
				    ActionForm form,
				    String callStatusId)
	throws Exception {

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}


        /*
	Troubleshooter troubleshooterEjb = factory.getTroubleshooterAPI();
	if( null == callId || "".equals(callId)) {
	    callId = (String)session.getAttribute("Call.id");
	}
	CallData callDetail = troubleshooterEjb.getCall(Integer.parseInt(callId));

	CallPropertyDataVector callPropertyDetailVec = troubleshooterEjb.getCallPropertyVec(Integer.parseInt(callId));


	CallOpDetailForm detailForm = (CallOpDetailForm) form;
	detailForm.setCallDetail(callDetail);

	//initialize the comstants lists for states and contries
	initConstantList(request);

         */
    }



    /**
     * <code>AddCallNote</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void addCallNote(
				   HttpServletRequest request,
				   ActionForm form,
                                   String callId)
	throws Exception {

	CallOpNoteDetailForm dForm = (CallOpNoteDetailForm) form;
        HttpSession session = request.getSession();

	dForm = new CallOpNoteDetailForm();

	if( null == callId || "".equals(callId)) {
	    callId = (String)session.getAttribute("Call.id");
	}

        CallOpDetailForm callForm = (CallOpDetailForm) session.getAttribute("CALL_OP_DETAIL_FORM");
        if (null != callForm && null != callId && ! "".equals(callId)) {
            editCallDetail(request, callForm, callId);
        }

        dForm.setCallId(callId);

        session.setAttribute("CALL_OP_NOTE_DETAIL_FORM", dForm);
	//session.setAttribute("Call.id", "");

    }


    /**
     * <code>editCallDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param callId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void editCallNote(
				    HttpServletRequest request,
				    ActionForm form,
				    String callId,
                                    String noteId)
	throws Exception {

	CallOpNoteDetailForm dForm = (CallOpNoteDetailForm) form;

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}

	Troubleshooter  troubleshooterEjb   = factory.getTroubleshooterAPI();

	if( null == callId || "".equals(callId)) {
	    callId = (String)session.getAttribute("Call.id");
	}

        CallOpDetailForm callForm = (CallOpDetailForm) session.getAttribute("CALL_OP_DETAIL_FORM");
        if (null != callForm && null != callId && ! "".equals(callId)) {
            editCallDetail(request, callForm, callId);
        }

        dForm.setCallId(callId);

        CallPropertyData noteD = troubleshooterEjb.getCallProperty(Integer.parseInt(noteId));

        dForm.setNoteDetail(noteD);

        session.setAttribute("CALL_OP_NOTE_DETAIL_FORM", dForm);
	//session.setAttribute("Call.id", callId);

    }



    /**
     * <code>saveCallNote</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors saveCallNote(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {

	 ActionErrors lUpdateErrors = new ActionErrors();

         HttpSession session = request.getSession();
	 APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	 if (null == factory) {
            throw new Exception("Without APIAccess.");
	 }

        CallOpNoteDetailForm dForm = (CallOpNoteDetailForm) form;

        if(dForm != null){
            if (dForm.getNoteDetail().getShortDesc().trim().length() == 0) {
                lUpdateErrors.add("notes",  new ActionError("variable.empty.error", "Note Description"));
            }
            if (dForm.getNoteDetail().getValue().trim().length() == 0) {
                lUpdateErrors.add("notes",  new ActionError("variable.empty.error", "Note Comments"));
            }
        }

	if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        int callId = Integer.parseInt(dForm.getCallId());

        Troubleshooter troubleshooterEjb = factory.getTroubleshooterAPI();

	CallPropertyData noteD = dForm.getNoteDetail();

	noteD.setCallId(callId);
	noteD.setCallPropertyStatusCd("ACTIVE");
	noteD.setCallPropertyTypeCd(RefCodeNames.CALL_PROPERTY_TYPE_CD.CALL_NOTE);

        String userName = (String)session.getAttribute(Constants.USER_NAME);
        noteD.setAddBy(userName);
	noteD.setModBy(userName);

	troubleshooterEjb.addCallProperty(noteD);

	return lUpdateErrors;

    }


    /**
     * <code>AddCallDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void addCallDetail(
				   HttpServletRequest request,
				   ActionForm form )
	throws Exception {

	CallOpDetailForm dForm = (CallOpDetailForm) form;

	dForm = new CallOpDetailForm();
        initUserList(request, dForm, "detail");

        HttpSession session = request.getSession();
        session.setAttribute("CALL_OP_DETAIL_FORM", dForm);
	session.setAttribute("Call.id", "");

	//initialize the comstants lists
	initConstantList(request);
    }



    /**
     * <code>editCallDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param callId a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public static void editCallDetail(
				    HttpServletRequest request,
				    ActionForm form,
				    String callId)
	throws Exception {

	CallOpDetailForm dForm = (CallOpDetailForm) form;

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}


	Troubleshooter  troubleshooterEjb   = factory.getTroubleshooterAPI();

	if( null == callId || "".equals(callId)) {
	    callId = (String)session.getAttribute("Call.id");
	}

        CallDescData callDescD = troubleshooterEjb.getCallDesc(Integer.parseInt(callId));

        CallPropertyDataVector callNotesList =
                    troubleshooterEjb.getCallPropertyCollection(Integer.parseInt(callId),
                                        RefCodeNames.CALL_PROPERTY_TYPE_CD.CALL_NOTE);
        if (null != callNotesList) {
            dForm.setCallNotesList(callNotesList);
        }

        dForm.setCallDetail(callDescD.getCallDetail());
        dForm.setAccountName(callDescD.getAccountName());
        dForm.setSiteName(callDescD.getSiteName());
        dForm.setSiteCity(callDescD.getSiteCity());
        dForm.setSiteState(callDescD.getSiteState());
        dForm.setSiteZip(callDescD.getSiteZip());
        dForm.setClosedDate(callDescD.getCallDetail().getClosedDate());

        dForm.setStatusCd(callDescD.getCallDetail().getCallStatusCd());
        dForm.setSeverityCd(callDescD.getCallDetail().getCallSeverityCd());

        if( 0 != callDescD.getCallDetail().getOrderId() ) {
            Order           orderEjb            = factory.getOrderAPI();
            OrderStatusDescData orderStatusDescD = orderEjb.getOrderStatusDesc(
                                            callDescD.getCallDetail().getOrderId() );
            if (null != orderStatusDescD && null != orderStatusDescD.getOrderDetail()) {
                OrderData orderStatusD = orderStatusDescD.getOrderDetail();
                dForm.setErpOrderNum(String.valueOf(orderStatusD.getErpOrderNum()));
                dForm.setWebOrderNum(String.valueOf(orderStatusD.getOrderNum()) );
                dForm.setCustPoNum(orderStatusD.getRequestPoNum());

                String erpPoNum = new String("");
                if ( null != orderStatusDescD.getOrderItemList() && 0 < orderStatusDescD.getOrderItemList().size() ) {
                    OrderItemDataVector orderItemV = orderStatusDescD.getOrderItemList();
                    for (int i = 0 ; i < orderItemV.size(); i++) {
                        OrderItemData orderItemD = (OrderItemData)orderItemV.get(i);
                        if ( null != orderItemD.getErpPoNum() && ! "".equals(orderItemD.getErpPoNum()) ) {
                            erpPoNum = orderItemD.getErpPoNum();
                            break;
                        }
                    }
                    dForm.setErpPoNum(erpPoNum);
                }
            }
        }

        if( 0 != callDescD.getCallDetail().getAccountId() ) {
            dForm.setAccountId(String.valueOf(callDescD.getCallDetail().getAccountId()) );
        }
        if( 0 != callDescD.getCallDetail().getSiteId() ) {
            dForm.setSiteId(String.valueOf(callDescD.getCallDetail().getSiteId()) );

            // get the orders associated with this site for 1-year time in descending order by order date
            OrderStatusCriteriaData orderCriteria = OrderStatusCriteriaData.createValue();
            IdVector storeIds = new IdVector();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                storeIds = appUser.getUserStoreAsIdVector();
            }
            orderCriteria.setSiteId(String.valueOf(callDescD.getCallDetail().getSiteId()));
            Date orderEndDate = callDescD.getCallDetail().getAddDate();

            Calendar gCalendar = Calendar.getInstance();
            gCalendar.setTime(orderEndDate);

            int year = gCalendar.get(Calendar.YEAR) - 1;
            gCalendar.set(Calendar.YEAR, year);
            Date orderBeginDate = gCalendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String orderBeginDateS = simpleDateFormat.format(orderBeginDate);
            String orderEndDateS = simpleDateFormat.format(orderEndDate);

            orderCriteria.setOrderDateRangeBegin(orderBeginDateS);
            orderCriteria.setOrderDateRangeEnd(orderEndDateS);

            Order orderEjb = factory.getOrderAPI();
            OrderStatusDescDataVector orderStatusDescV = orderEjb.getOrderStatusDescCollection(orderCriteria, storeIds);
            dForm.setOrderStatusDescList(orderStatusDescV);
        }
        if( 0 != callDescD.getCallDetail().getAssignedToId() ) {
            dForm.setAssignedToId(String.valueOf(callDescD.getCallDetail().getAssignedToId()) );
            User userEjb = factory.getUserAPI();
            UserData userD = userEjb.getUser(callDescD.getCallDetail().getAssignedToId());
            dForm.setAssignedToName(userD.getUserName());
        }

        session.setAttribute("CALL_OP_DETAIL_FORM", dForm);
	session.setAttribute("Call.id", callId);

	//initialize the comstants lists
	initConstantList(request);
        initUserList(request, form, "detail");
    }



    /**
     * <code>fetchOrderInfo</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void fetchOrderInfo(
				    HttpServletRequest request,
				    ActionForm form )
	throws Exception {

	CallOpDetailForm dForm = (CallOpDetailForm) form;

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}


	Order  orderEjb   = factory.getOrderAPI();
        User userBean = factory.getUserAPI();
        Site siteBean = factory.getSiteAPI();

        String changeField =  request.getParameter("changefield");
        if ( null == changeField || "".equals(changeField) ) {
            return;
        }
        else if("siteId".equals(changeField)){

          int formSiteId = Integer.parseInt(dForm.getSiteId());
          SiteData sd = null;
          dForm.setAccountId("");
          dForm.setSiteId("");

          sd = siteBean.getSite(formSiteId, 0, false, SessionTool.getCategoryToCostCenterView(session, formSiteId));
          if(sd != null){

            int acctId = sd.getAccountBusEntity().getBusEntityId();
            int siteId = sd.getBusEntity().getBusEntityId();
            Integer aId = new Integer(acctId);
            Integer sId = new Integer(siteId);
            dForm.setAccountId(aId.toString());
            dForm.setSiteId(sId.toString());
          }
        }
        else {
            OrderStatusCriteriaData orderCriteriaD = OrderStatusCriteriaData.createValue();
            IdVector storeIds = new IdVector();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                storeIds = appUser.getUserStoreAsIdVector();
            }
            boolean emptyFlag = false;

            if ( "erpOrderNum".equals(changeField) ) {
                if ( dForm.getErpOrderNum().trim().length() > 0 ) {
                    orderCriteriaD.setErpOrderNum(dForm.getErpOrderNum());
                }
                else {
                    emptyFlag = true;
                }
            }
            else if ( "webOrderNum".equals(changeField) ) {
                if ( dForm.getWebOrderNum().trim().length() > 0 ) {
                    orderCriteriaD.setWebOrderConfirmationNum(dForm.getWebOrderNum());
                }
                else {
                    emptyFlag = true;
                }
            }
            else if ( "custPoNum".equals(changeField) ) {
                if ( dForm.getCustPoNum().trim().length() > 0 ) {
                    orderCriteriaD.setCustPONum(dForm.getCustPoNum());
                }
                else {
                    emptyFlag = true;
                }
            }
            else if ( "erpPoNum".equals(changeField) ) {
                if ( dForm.getErpPoNum().trim().length() > 0 ) {
                    orderCriteriaD.setErpPONum(dForm.getErpPoNum());
                }
                else {
                    emptyFlag = true;
                }
            }
            else {
                emptyFlag = true;
            }

            int orderStatusId = 0 ;
            String erpOrderNum = new String("");
            String webOrderNum = new String("");
            String custPoNum = new String("");
            String erpPoNum = new String("");
            int siteId = 0;
            int acctId = 0;

            if (false == emptyFlag ) {
                // search order must have userId or/and userTypeCd
                String userId = (String)session.getAttribute(Constants.USER_ID);
                String userType = (String)session.getAttribute(Constants.USER_TYPE);
                if ( null == userId ) userId = new String("");
                if ( null == userType ) userType = new String("");
                orderCriteriaD.setUserId(userId);
                orderCriteriaD.setUserTypeCd(userType);

                OrderStatusDescDataVector orderStatusDescV = orderEjb.getOrderStatusDescCollection(orderCriteriaD, storeIds);
                if (null != orderStatusDescV && orderStatusDescV.size() > 0 ) {
                    OrderStatusDescData orderStatusDesc = (OrderStatusDescData) orderStatusDescV.get(0);
                    //  user userId and site name to get siteId and accountID
                    int uId = orderStatusDesc.getOrderDetail().getUserId();
                    String siteName = orderStatusDesc.getOrderDetail().getOrderSiteName();
                    String acctErpNum = orderStatusDesc.getOrderDetail().getAccountErpNum();
                    SiteData sd = siteBean.getSite(orderStatusDesc.getOrderDetail().getSiteId(), 0, false, SessionTool.getCategoryToCostCenterView(session, orderStatusDesc.getOrderDetail().getSiteId()));

                    siteId = sd.getBusEntity().getBusEntityId();
                    acctId = sd.getAccountBusEntity().getBusEntityId();

                    orderStatusId = orderStatusDesc.getOrderDetail().getOrderId();
                    erpOrderNum = String.valueOf(orderStatusDesc.getOrderDetail().getErpOrderNum());

                    webOrderNum = orderStatusDesc.getOrderDetail().getOrderNum();
                    custPoNum = orderStatusDesc.getOrderDetail().getRequestPoNum();

                    if ( null != orderStatusDesc.getOrderItemList() && orderStatusDesc.getOrderItemList().size() > 0 ) {
                        OrderItemDataVector orderItemV = orderStatusDesc.getOrderItemList();
                        for (int i = 0 ; i < orderStatusDesc.getOrderItemList().size(); i++) {
                            OrderItemData orderItemD = (OrderItemData)orderItemV.get(i);
                            if ( null != orderItemD.getErpPoNum() && ! "".equals(orderItemD.getErpPoNum()) ) {
                                erpPoNum = orderItemD.getErpPoNum();
                                break;
                            }
                        } // end of loop for erpPoList
                    } // end of if erpPoList exists
                } // end of if orderStatusDescV exists
            } // end of if emptyFlag == false

            dForm.getCallDetail().setOrderId(orderStatusId);
            dForm.setErpOrderNum(erpOrderNum);
            dForm.setWebOrderNum(webOrderNum);
            dForm.setCustPoNum(custPoNum);
            dForm.setErpPoNum(erpPoNum);

            if(siteId != 0 && acctId != 0){
              Integer sSiteId = new Integer(siteId);
              Integer sAcctId = new Integer(acctId);
              dForm.setAccountId(sAcctId.toString());
              dForm.setSiteId(sSiteId.toString());
           }else{
              dForm.setAccountId("");
              dForm.setSiteId("");
           }

        }



        return;
    }


    /**
     * <code>saveCallDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors saveCallDetail(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();

        CallOpDetailForm dForm = (CallOpDetailForm) form;

	int orgCallId = dForm.getCallDetail().getCallId();
        int orgAssignedToId = dForm.getCallDetail().getAssignedToId();
        String orgStatusCd = dForm.getCallDetail().getCallStatusCd();
        String orgSeverityCd = dForm.getCallDetail().getCallSeverityCd();

	// Get a reference to the admin facade.
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}

	Troubleshooter troubleshooterEjb = factory.getTroubleshooterAPI();

	CallData detail = dForm.getCallDetail();

        if (detail.getContactName().trim().length() == 0) {
            lUpdateErrors.add("calldetail",  new ActionError("variable.empty.error", "Contact Name"));
        }
        if (detail.getContactPhoneNumber().trim().length() == 0) {
            lUpdateErrors.add("calldetail",  new ActionError("variable.empty.error", "Contact Phone"));
        }
        if (dForm.getStatusCd().trim().length() == 0) {
            lUpdateErrors.add("calldetail",  new ActionError("variable.empty.error", "Call Status"));
        }
        if (detail.getCallTypeCd().trim().length() == 0) {
            lUpdateErrors.add("calldetail",  new ActionError("variable.empty.error", "Call Type"));
        }
        if (dForm.getSeverityCd().trim().length() == 0) {
            lUpdateErrors.add("calldetail",  new ActionError("variable.empty.error", "Call Severity"));
        }
        if (detail.getLongDesc().trim().length() == 0) {
            lUpdateErrors.add("calldetail",  new ActionError("variable.empty.error", "Call Description"));
        }
        if (detail.getComments().trim().length() == 0) {
            lUpdateErrors.add("calldetail",  new ActionError("variable.empty.error", "Call Comments"));
        }
	if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }


        // check if the accountId or siteId is correct
        if ( 0 == orgCallId ) {

            // check account id
            String accountId = dForm.getAccountId();
            if (null != accountId && !"".equals(accountId)) {
                Account accountEjb = factory.getAccountAPI();
                AccountData accountD = null;
                try {
                    accountD = accountEjb.getAccount(Integer.parseInt(accountId), 0);
                }
                catch (Exception e) {
                    accountD = null;
                }
                if( null == accountD) {
                    lUpdateErrors.add("calldetail", new ActionError("bad.account"));
                    return lUpdateErrors;
                }
            }

            // check site id
            String siteId = dForm.getSiteId();
            if (null != siteId && !"".equals(siteId)) {
                Site siteEjb = factory.getSiteAPI();
                SiteData siteD = null;
                if (null != accountId && !"".equals(accountId)) {
                    // if the site is belonging to some account
                    try {
                        siteD = siteEjb.getSite(Integer.parseInt(siteId), Integer.parseInt(accountId),false, SessionTool.getCategoryToCostCenterView(session, Integer.parseInt(siteId)));
                    }
                    catch (Exception e) {
                        siteD = null;
                    }
                }
                else {
                    // if no account assigned
                    try {
                        siteD = siteEjb.getSite(Integer.parseInt(siteId), 0, false, SessionTool.getCategoryToCostCenterView(session, Integer.parseInt(siteId)));
                    }
                    catch (Exception e) {
                        siteD = null;
                    }
                }
                if( null == siteD) {
                    lUpdateErrors.add("calldetail", new ActionError("bad.site"));
                    return lUpdateErrors;
                }
            }

        }

        String userName = (String)session.getAttribute(Constants.USER_NAME);
        String userId = (String)session.getAttribute(Constants.USER_ID);
        if ( 0 == orgCallId ) {
            detail.setAddBy(userName);
        }
        detail.setModBy(userName);
        detail.setOpenedById(Integer.parseInt(userId));
        detail.setClosedDate(dForm.getClosedDate());

        detail.setCallStatusCd(dForm.getStatusCd());
        detail.setCallSeverityCd(dForm.getSeverityCd());

        if( null != dForm.getAccountId() && ! "".equals(dForm.getAccountId()) ) {
            detail.setAccountId(Integer.parseInt(dForm.getAccountId()));
        }
        if( null != dForm.getSiteId() && ! "".equals(dForm.getSiteId()) ) {
            detail.setSiteId(Integer.parseInt(dForm.getSiteId()));
        }
        if( null != dForm.getAssignedToId() && ! "".equals(dForm.getAssignedToId()) ) {
            detail.setAssignedToId(Integer.parseInt(dForm.getAssignedToId()));
        }

        CallData newDetail = CallData.createValue();

        newDetail = troubleshooterEjb.addCall(detail);
        session.setAttribute("Call.id", String.valueOf(newDetail.getCallId()));


        // if the status cd is changed, log it into notes
        if ( 0 != orgCallId && ! orgStatusCd.equals(newDetail.getCallStatusCd()) ) {
            CallPropertyData noteD = CallPropertyData.createValue();
            noteD.setCallId(newDetail.getCallId());
            noteD.setCallPropertyStatusCd("ACTIVE");
            noteD.setCallPropertyTypeCd(RefCodeNames.CALL_PROPERTY_TYPE_CD.CALL_NOTE);
            noteD.setAddBy(userName);
            noteD.setModBy(userName);

            String noteDesc =  new String();
            noteDesc = new String("Call Status is changed from '" + orgStatusCd + "' to '"
                                + newDetail.getCallStatusCd() + "' by " + userName);

            noteD.setShortDesc(noteDesc);
            Date currentTime = new Date();
            noteD.setValue("Time: " + currentTime.toString() + "\n" + noteDesc);

            troubleshooterEjb.addCallProperty(noteD);
        }

        // if the severity cd is changed, log it into notes
        if ( 0 != orgCallId && ! orgSeverityCd.equals(newDetail.getCallSeverityCd()) ) {
            CallPropertyData noteD = CallPropertyData.createValue();
            noteD.setCallId(newDetail.getCallId());
            noteD.setCallPropertyStatusCd("ACTIVE");
            noteD.setCallPropertyTypeCd(RefCodeNames.CALL_PROPERTY_TYPE_CD.CALL_NOTE);
            noteD.setAddBy(userName);
            noteD.setModBy(userName);

            String noteDesc =  new String();
            noteDesc = new String("Call severity is changed from '" + orgSeverityCd + "' to '"
                                + newDetail.getCallSeverityCd() + "' by " + userName);

            noteD.setShortDesc(noteDesc);

            Date currentTime = new Date();
            noteD.setValue("Time: " + currentTime.toString() + "\n" + noteDesc);

            troubleshooterEjb.addCallProperty(noteD);
        }

        // send email to newly assigned user, log it into the notes.
        if (  orgAssignedToId != newDetail.getAssignedToId() ) {

            // Send out an email to the saaigned crc user .
            String mailmsg = "";

            User userEjb = factory.getUserAPI();
            UserInfoData assignedToUserInfo = userEjb.getUserContact(newDetail.getAssignedToId());
            int assignedUserId = assignedToUserInfo.getUserData().getUserId();
            EmailData emailD = assignedToUserInfo.getEmailData();

            if (null != emailD) {
                String subj = "Call tracking request assigned by: " + userName;

                mailmsg += "\n  Message: " + dForm.getEmailMessage();
                mailmsg += "\n\n  Call Desc: " + dForm.getCallDetail().getLongDesc();
                mailmsg += "\n  Contact Name: " + dForm.getCallDetail().getContactName();
		mailmsg += "\n  Contact Phone: " + dForm.getCallDetail().getContactPhoneNumber();
		mailmsg += "\n  Contact Email: " + dForm.getCallDetail().getContactEmailAddress();
                if ( 0 != newDetail.getAccountId() ) {
                    mailmsg += "\n  Account Id: " + dForm.getAccountId();
                    mailmsg += "\n  Account Name: " + dForm.getAccountName();
                }
                if ( 0 != newDetail.getSiteId() ) {
                    mailmsg += "\n  Site Id: " + dForm.getSiteId();
                    mailmsg += "\n  Site Name: " + dForm.getSiteName();
                }
                mailmsg += "\n\n  Call Type: " + dForm.getCallDetail().getCallTypeCd();
                mailmsg += "\n  Call Severity: " + dForm.getCallDetail().getCallSeverityCd();
                mailmsg += "\n  Opened By: " + newDetail.getAddBy();
                mailmsg += "\n  Opened Date: " + newDetail.getAddDate().toString();

                /*//
                if (0 != dForm.getCallDetail().getOrderStatusId() ) {
                    mailmsg += "\n\n  Order Status Id: " + dForm.getCallDetail().getOrderStatusId();
                    mailmsg += "\n  ERP order # : " + dForm.getErpOrderNum();
                    mailmsg += "\n  Web order # : " + dForm.getWebOrderNum();
                    mailmsg += "\n  Customer PO # : " + dForm.getCustPoNum();
                    mailmsg += "\n  ERP PO # : " + dForm.getErpPoNum();
                }
                 */

                boolean sendFlag = false;
                String failReason =  new String("");
		try {
                    EmailClient emc = factory.getEmailClientAPI();
                    emc.send(emailD.getEmailAddress(),
			     emc.getDefaultEmailAddress(),
			     subj,
			     mailmsg, Constants.EMAIL_FORMAT_PLAIN_TEXT,
			     0,assignedUserId);
                    sendFlag = true;
		}
		catch (Exception e) {
                    sendFlag = false;
                    failReason = e.toString();
                }

                // add a note to this call for the email sent.
                CallPropertyData noteD = CallPropertyData.createValue();
                noteD.setCallId(newDetail.getCallId());
                noteD.setCallPropertyStatusCd("ACTIVE");
                noteD.setCallPropertyTypeCd(RefCodeNames.CALL_PROPERTY_TYPE_CD.CALL_NOTE);
                noteD.setAddBy(userName);
                noteD.setModBy(userName);
                String noteDesc =  new String();
                StringBuffer noteComment = new StringBuffer();
                if ( true == sendFlag ) {
                    noteDesc = new String("Sent email notification to " + assignedToUserInfo.getUserData().getUserName());
                    noteComment.append("Sent email notification to ");
                    noteComment.append(assignedToUserInfo.getUserData().getUserName());

                    noteComment.append("\n\n Email Address: ");
                    noteComment.append(emailD.getEmailAddress());
                    noteComment.append("\n Sent Date: ");
                    Date currentDate =  new Date();
                    noteComment.append(currentDate.toString());

                    noteComment.append("\n\n Email Message: ");
                    noteComment.append(dForm.getEmailMessage());

                    noteD.setShortDesc(noteDesc);
                    noteD.setValue(noteComment.toString());
                    noteD.setAddBy(userName);
                    noteD.setModBy(userName);

                    troubleshooterEjb.addCallProperty(noteD);

                }
                else {
                    //noteDesc = new String("Failed to send email notification to " + assignedToUserInfo.getUserData().getUserName());
                    //noteComment.append("Failed to Send email notification to ");
                    //noteComment.append(assignedToUserInfo.getUserData().getUserName());
                    //noteComment.append("\n Failed reason: \n");
                    //noteComment.append(failReason);
                }

            } // end of if the assigne to user has email address

        } // end of if the call is not assigned yet.


        return lUpdateErrors;
    }


}

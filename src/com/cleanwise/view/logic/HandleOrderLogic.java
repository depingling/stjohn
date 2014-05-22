package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.Workflow;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.ConsolidatedCartView;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemJoinDataVector;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartDistDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.pdf.PdfOrder;
import com.cleanwise.view.utils.pdf.PdfOrderStatus;

/**
 *  <code>HandleOrderLogic</code> implements the logic
 *  to manipulate Orders which require approval or rejection.
 *
 *@author     durval
 *@created    January 7, 2002
 */
public class HandleOrderLogic {
	static final Logger log = Logger.getLogger(HandleOrderLogic.class);

    static final int REJECT = 90;
    static final int APPROVE = 91;
    static final int VIEW = 92;
    static final int EDIT = 93;
    static final int REORDER = 94;

    public static void processReq(HttpServletRequest request, int pOrderId,
                                  int pReq) {

        HttpSession session = request.getSession();
        OrderJoinData ojd = new OrderJoinData();


        // Reset the order information.
        session.setAttribute("order", ojd);

        String cuser = (String)session.getAttribute(Constants.USER_NAME);

        try {

            APIAccess factory = new APIAccess();
            Order orderBean = factory.getOrderAPI();
            Workflow workflowBean = factory.getWorkflowAPI();
            IntegrationServices isBean = factory.getIntegrationServicesAPI();
            ojd = orderBean.fetchOrder(pOrderId);
            // Get the site for the new order from the previous order.
            Site siteEjb = factory.getSiteAPI();
            int siteId = ojd.getOrder().getSiteId();
            SiteData sd = siteEjb.getSite(siteId,0, false, SessionTool.getCategoryToCostCenterView(session, siteId));
            int orderSiteId = sd.getBusEntity().getBusEntityId();

            switch (pReq) {

                case REJECT:
                    LogOnLogic.siteShop(request, orderSiteId);
                    ojd = orderBean.updateOrderInfo
                        (pOrderId,
                         RefCodeNames.ORDER_STATUS_CD.REJECTED,
                         cuser);
                    // Le the system take care of sending
                    // order rejection emails as part of the
                    // workflow processing.
                    //sendOrderRejectionEmail(ojd);

                    break;

                case EDIT:
                    LogOnLogic.siteShop(request, orderSiteId);
                    CleanwiseUser user =
                        (CleanwiseUser) session.getAttribute(Constants.APP_USER);

                    String userName =
                      user.getUser().getFirstName() + " " + user.getUser().getLastName();
                    orderBean.setMetaAttribute(pOrderId,
                        Order.MODIFICATION_STARTED,
                        userName,
                        cuser);
                    //ojd = orderBean.updateOrderInfo
                    //    (pOrderId,
                    //     RefCodeNames.ORDER_STATUS_CD.CANCELLED,
                    //     cuser);
                    break;
                case VIEW:
                    //ojd = orderBean.fetchOrder(pOrderId);
                    //Can approve?
                    CleanwiseUser appUser =
                        (CleanwiseUser)session.getAttribute("ApplicationUser");
                    IdVector notesUserApproveIdV =
                      orderBean.getPropertiesUserCanApprove(appUser.getUser(), ojd.getOrderId());
                    session.setAttribute("note.approve.ids",notesUserApproveIdV);
                    break;

                default:
                    LogOnLogic.siteShop(request, orderSiteId);
                    ojd = orderBean.fetchOrder(pOrderId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        session.setAttribute("order", ojd);

        return;
    }

    /**
     *  Pick out the information from the form to send an email.
     *
     *@param  request
     */
    public static void viewOrder(HttpServletRequest request, int pOrderId) {
        processReq(request, pOrderId, VIEW);
    }

    public static ActionErrors rejectOrder(HttpServletRequest request, int pOrderId,
                                   MessageResources pMsgRes)
    throws Exception
    {
        ActionErrors ae = new ActionErrors();
        ae = addCustomerNote(request,pOrderId);
        if(ae.size()>0) {
            return ae;
        }
        processReq(request, pOrderId, REJECT);
        return ae;
    }


    public static ActionErrors editOrder(HttpServletRequest request,
                                         int pOrderId)
    throws Exception
    {
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);
        if (appUser.getSite() == null) {
        	ActionErrors ae = new ActionErrors();
        	String errorMess = ClwI18nUtil.getMessage(request,"error.noLocationSelected");
        	ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }
        int siteIdOld = appUser.getSite().getSiteId();

        ActionErrors ae = addCustomerNote(request,pOrderId);
        if(ae.size()>0) {
            return ae;
        }
        processReq(request, pOrderId, EDIT);
        ae = OrderOpLogic.reorder2(request);
        int siteIdNew = appUser.getSite().getSiteId();
        if (siteIdOld != siteIdNew){
            refreshOGuides(request);
            UserShopLogic.init(request, (UserShopForm)session.getAttribute(Constants.USER_SHOP_FORM));
        }
        return ae;
    }

    private static void refreshOGuides(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
              ShoppingServices sEjb = factory.getShoppingServicesAPI();
              OrderGuideDataVector allOGs = new OrderGuideDataVector();
              Integer catalogId = (Integer)session.getAttribute(Constants.CATALOG_ID);
              CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);
              int siteId = appUser.getSite().getSiteId();
              int userId = appUser.getUserId();
              int accountId = appUser.getUserAccount().getAccountId();
                                                      
              OrderGuideDataVector templateOrderGuideDV = sEjb.getTemplateOrderGuides(catalogId, siteId);
              for(int i=0; i<templateOrderGuideDV.size(); i++){
            	  OrderGuideData tOG = (OrderGuideData)templateOrderGuideDV.get(i);
            	  allOGs.add(tOG);
              }

              OrderGuideDataVector userOrderGuideDV = sEjb.getUserOrderGuides(userId, catalogId, siteId);
              for(int i=0; i<userOrderGuideDV.size(); i++){
            	  OrderGuideData uOG = (OrderGuideData)userOrderGuideDV.get(i);
            	  allOGs.add(uOG);
              }

              OrderGuideDataVector customOrderGuideDV = sEjb.getCustomOrderGuides(accountId, siteId);
              for(int i=0; i<customOrderGuideDV.size(); i++){
            	  OrderGuideData uOG = (OrderGuideData)customOrderGuideDV.get(i);
            	  allOGs.add(uOG);
              }

              if(allOGs.size()>0 && !allOGs.isEmpty()){

            	  DisplayListSort.sort(allOGs, "short_desc");
            	  request.getSession().setAttribute(SessionAttributes.ORDER_GUIDE.ALL_ORDER_GUIDES, allOGs);
              }else{
            	  request.getSession().setAttribute(SessionAttributes.ORDER_GUIDE.ALL_ORDER_GUIDES, new OrderGuideDataVector());
              }

    }

    public static ActionErrors copyOrderToCart(HttpServletRequest request,
                                               int pOrderId) {
        processReq(request, pOrderId, REORDER);

        try {
            ActionErrors ae = OrderOpLogic.reorder2(request);
            if(ae.size()>0) {
                return ae;
            }
            HttpSession session = request.getSession();
            ShoppingCartData scD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
            scD.setPrevOrderData(null);
            ae = ShoppingCartLogic.saveShoppingCart(session);
            if(ae.size()>0) {
                return ae;
            }
            OrderJoinData order = (OrderJoinData)session.getAttribute("order");
            int orderSiteId = order.getOrder().getSiteId();
           if(!scD.getSite().hasModernInventoryShopping())          {
               UserShopLogic.saveInventoryOnHandValues(request, orderSiteId, scD.getItems(), false);
           }
            return ae;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ActionErrors();
    }

    public static ActionErrors approveOrder(OrderOpDetailForm sForm, HttpServletRequest request, int pOrderId)
    throws Exception
    {
        ActionErrors ae = new ActionErrors();
        ae = addCustomerNote(request,pOrderId);
        if(ae.size()>0) {
            return ae;
        }
        Enumeration paramEnum = request.getParameterNames();
        boolean approveAllPossible = false;
        HashSet notesToApprove = new HashSet();
        String action = null;
        while(paramEnum.hasMoreElements()) {
          String paramName = (String) paramEnum.nextElement();
          if(paramName.equals("action")) {
            action = request.getParameter("action");
          }
          if(paramName.equals("approveAll")) {
            String param = request.getParameter(paramName);
            if("true".equalsIgnoreCase(param)) {
              approveAllPossible = true;
              notesToApprove = null;
              break;
            }
          }
          if(paramName.startsWith("approveNote")) {
            String param = request.getParameter(paramName);
            int orderPropertyId = Integer.parseInt(param);
            notesToApprove.add(new Integer(orderPropertyId));
          }
        }
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Order orderBean = factory.getOrderAPI();
        IntegrationServices isBean = factory.getIntegrationServicesAPI();
        Site sbean = factory.getSiteAPI();

        OrderJoinData orderJD = orderBean.fetchOrder(pOrderId);
        session.setAttribute("order",orderJD);

        //OrderJoinData orderJD = (OrderJoinData) session.getAttribute("order");
        LogOnLogic.siteShop(request, orderJD.getOrder().getSiteId());

        CleanwiseUser appUser =
            (CleanwiseUser)session.getAttribute("ApplicationUser");

        IdVector notesUserApproveIdV =
          orderBean.getPropertiesUserCanApprove(appUser.getUser(), orderJD.getOrderId());

        session.setAttribute("note.approve.ids",notesUserApproveIdV);

        OrderPropertyDataVector opDV =
               orderJD.getOrderProperties("Workflow Note",
                              RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                              RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        IdVector opIdV = new IdVector();

        OrderPropertyDataVector notApprovedNotes = new OrderPropertyDataVector();
        for(Iterator iter=opDV.iterator(); iter.hasNext();) {
          OrderPropertyData opD = (OrderPropertyData) iter.next();
          Integer opIdI = new Integer(opD.getOrderPropertyId());
          if( notesUserApproveIdV.contains(opIdI) &&
             (approveAllPossible || notesToApprove.contains(opIdI))) {
            opIdV.add(opIdI);
          } else {
            if(opD.getApproveDate()==null) {
              notApprovedNotes.add(opD);
            }
          }
        }

        AccountData accountD = appUser.getUserAccount();
        boolean allowPoEntry = true;
        if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
          equals(accountD.getCustomerSystemApprovalCd())){
          allowPoEntry = false;
        }
        boolean f_showPO = true;
        if(orderJD.getOrderCC()!=null) f_showPO = false;

        SiteData siteD = appUser.getSite();
        if(siteD.getBlanketPoNum() != null &&
           siteD.getBlanketPoNum().getBlanketPoNumId() != 0){
          allowPoEntry = false;
        }


        String poNum = orderJD.getOrder().getRequestPoNum();
        if(allowPoEntry && f_showPO){
          //String requestPoNum = (String) request.getParameter("requestPoNum");
          String requestPoNum = sForm.getRequestPoNum();
          if(!Utility.isSet(requestPoNum) && appUser.getPoNumRequired()) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.requestPoNumberRequered",null);
            ae.add("error",
               new ActionError("error.simpleGenericError", errorMess));
            return ae;
          }
          poNum = requestPoNum;
        }

        HandleOrderLogic handleOrderLogic =
                (HandleOrderLogic) ClwCustomizer.getJavaObject(request,"com.cleanwise.view.logic.HandleOrderLogic");
        ae = handleOrderLogic.validatePoNum(request, poNum);
        if(!ae.isEmpty()) {
            return ae;
        }


        //Verify the date
        //String pattern = I18nUtil.getDatePattern(ClwI18nUtil.getUserLocale(request));
        String pattern = ClwI18nUtil.getDatePattern(request);
        SimpleDateFormat sdfInp = new SimpleDateFormat(pattern);
        String dateS  = request.getParameter("approveDate");
        Date processDate = null;
        if(Utility.isSet(dateS)) {
          try {
            processDate = sdfInp.parse(dateS);
          }catch(Exception exc) {}
          if((processDate==null) || (!sdfInp.format(processDate).equals(dateS))) {
            Object[] params = new Object[1];
            params[0] = dateS;
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.wrongDateFormat",params);
            ae.add("error",new ActionError
                 ("error.simpleGenericError", errorMess));
            return ae;
          }

          SimpleDateFormat sdfUs = new SimpleDateFormat("MM/dd/yyyy");
          Date currDate = new Date();
          currDate = sdfUs.parse(sdfUs.format(currDate));
          int compare = processDate.compareTo(currDate);
          if(compare < 0) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.dateCan'tBeBeforeCurrentDate",null);
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
          } else if(compare >= 0) {
            if(notApprovedNotes.size()>0) {
              String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.someNotesAreNotCleared",null);
              ae.add("error",new ActionError("error.simpleGenericError",errorMess));
              int ind=0;
              for(Iterator iter=notApprovedNotes.iterator(); iter.hasNext();ind++) {
                     OrderPropertyData opD = (OrderPropertyData) iter.next();
                 Object[] params = new Object[1];
                 params[0] = opD.getValue();
                 errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noteIsNotCleared",params);
                 ae.add("error"+ind,new ActionError("error.simpleGenericError",errorMess));
              }
              return ae;
            }
          }
        }else if("ApproveOn".equals(action)){
        	String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterValidDate",null);
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }
        orderJD = orderBean.approveOrder(pOrderId, opIdV, processDate, poNum,
        		appUser.getUser().getUserId(), appUser.getUser().getUserName());
        isBean.updateJanitorsCloset(orderJD, true);
        sbean.updateBudgetSpendingInfo(siteD);
        session.setAttribute("order", orderJD);
        return ae;

    }

    /**
     * <code>printDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors printDetail(HttpServletResponse response,
                                           HttpServletRequest request,
                                           ActionForm form, int orderId)
                                    throws Exception {

        HttpSession session = request.getSession();
        CheckoutForm sForm = (CheckoutForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        Store storeEjb = factory.getStoreAPI();
        Order orderEjb = factory.getOrderAPI();
        PropertyService propEjb = factory.getPropertyServiceAPI();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute("ApplicationUser");

        OrderJoinData order = orderEjb.fetchOrder(orderId);
        //XXX this is wrong, should use store out of order!
        int storeId = order.getOrder().getStoreId();
        if(storeId==0) storeId = 1;
        StoreData storeD = storeEjb.getStore(storeId);
        String currentLocaleCd  = (String) session.getAttribute(Constants.CATALOG_LOCALE);

        String imgPath = ClwCustomizer.getCustomizeImgElement(request,"pages.store.logo1.image");

        //sets the content type so the browser knows this is a pdf
        response.setContentType("application/pdf");
        String browser = (String)  request.getHeader("User-Agent");
        boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        if (!isMSIE6){
        	response.setHeader("extension", "pdf");
        	response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");
        }

        //gets the references to the objects our print function needs
        boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
      (storeD.getStoreType().getValue());
  // for store type other than MLA, we need display distributor info
  // (which items belong to which distributor)
  ByteArrayOutputStream out = new ByteArrayOutputStream();

        if (sForm.getOrderServiceFlag() == true) {
           PdfOrder pdf = new PdfOrder(request,currentLocaleCd);
           if (!isaMLAStore) {
                ShoppingCartDistDataVector cartDistV = sForm.getCartDistributors();
                pdf.generateDistPdfforService(sForm, appUser, storeD, out, imgPath, order, cartDistV);
            } else {
                List items = sForm.getServices();
                pdf.generatePdfforService(sForm, appUser, storeD, out, imgPath, order, items);
            }
        } else {
            if (!isaMLAStore) {
                PdfOrder pdf = null;
//////////////////
                AccountData accountD = appUser.getUserAccount();
                int accountId = accountD.getAccountId();
                String pdfClass = null;
                try {
                  pdfClass = propEjb.getBusEntityProperty(accountId, RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS);
                }
                catch (DataNotFoundException ex) {
                  pdfClass = null;
                }
                catch (RemoteException ex) {
                  pdfClass = null;
                  ex.getStackTrace();
                }

                if(Utility.isSet(pdfClass)) {
                    try {
                        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pdfClass);
                        pdf = (PdfOrder) clazz.newInstance();
                        Properties props = setSpecialPropertyForPrintPdf(accountId,factory, sForm.getOrderResult().getSiteId());
                        pdf.setMiscProperties(props);
                        List items = sForm.getItems();
                        pdf.init(request, currentLocaleCd);
                        pdf.generatePdf(sForm, appUser, storeD, out, imgPath, order, items);

                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }

                } else {
                    pdf = new PdfOrder(request,currentLocaleCd);
                    ShoppingCartDistDataVector cartDistV = sForm.getCartDistributors();
                    pdf.generateDistPdf(sForm, appUser, storeD, out, imgPath, order, cartDistV);
                }
            } else {
                List items = sForm.getItems();
                PdfOrder pdf = null;
                AccountData accountD = appUser.getUserAccount();
                int accountId = accountD.getAccountId();
                String pdfClass = null;
                try {
                  pdfClass = propEjb.getBusEntityProperty(accountId, RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS);
                }
                catch (DataNotFoundException ex) {
                  pdfClass = null;
                }
                catch (RemoteException ex) {
                  pdfClass = null;
                  ex.getStackTrace();
                }

                if(Utility.isSet(pdfClass)) {
                    try {
                        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pdfClass);
                        pdf = (PdfOrder) clazz.newInstance();
                        Properties props = setSpecialPropertyForPrintPdf(accountId,factory, sForm.getOrderResult().getSiteId());
                        pdf.setMiscProperties(props);
                    } catch (Exception exc) {
                        log.info("!!!!ATTENTION failed creating PdfOrder using class name: "+pdfClass);
                    }

                }
                if (pdf==null) {
                    pdf = new PdfOrder();
                }
                pdf.init(request, currentLocaleCd);
                pdf.generatePdf(sForm, appUser, storeD, out, imgPath, order, items);
            }
        }
        try{
	        response.setContentLength(out.size());
	        out.writeTo(response.getOutputStream());
	        response.flushBuffer();
	        response.getOutputStream().close();

	        return new ActionErrors();
        }catch(IOException e){
        	response.getOutputStream().close();
	        return new ActionErrors();
        }
    }

    public static Properties setSpecialPropertyForPrintPdf(int pAccountId, APIAccess pFactory, int pSiteId) throws Exception {
        Properties result = new Properties();
        // set CUST_MAJ
        PropertyService propEjb = pFactory.getPropertyServiceAPI();
        try {
            String custMaj = propEjb.getBusEntityProperty(pAccountId, RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ);
            result.put(RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, custMaj);
        } catch (DataNotFoundException ex) {
            log.info("!!!!ATTENTION failed to find CUST_MAJ for account. Account id: "+pAccountId);
        }
        // set distr name
        try {
            Distributor distrBean = pFactory.getDistributorAPI();
            DistributorData distr = distrBean.getSubDistributorForSite(pSiteId);
            String distrName = distr.getBusEntity().getShortDesc();
            String distrCompanyCode = distr.getDistributorsCompanyCode();
            if (Utility.isSet(distrCompanyCode)) {
                String p1 = distrCompanyCode.substring(0,3);
                String p2 = distrCompanyCode.substring(3);
                distrName = distrName + " " + p1 + "_" + p2;
            }
            result.put(Constants.DISTRIBUTOR_NAME, distrName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * <code>printOrderStatus</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors printOrderStatus(HttpServletResponse response,
                                           HttpServletRequest request,
                                           ActionForm form)
                                    throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        OrderOpDetailForm sForm = (OrderOpDetailForm)
                              session.getAttribute("ORDER_OP_DETAIL_FORM");
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        Store storeEjb = factory.getStoreAPI();
        PropertyService propEjb = factory.getPropertyServiceAPI();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        "ApplicationUser");


        //XXX this is wrong, should use store out of order!
        OrderStatusDescData orderStatusDesc = sForm.getOrderStatusDetail();
        int storeId = orderStatusDesc.getOrderDetail().getStoreId();
        StoreData storeD = storeEjb.getStore(storeId);
        String localeCd = orderStatusDesc.getOrderDetail().getLocaleCd();
        String imgPath = ClwCustomizer.getCustomizeImgElement(request,"pages.store.logo1.image");

        //sets the content type so the browser knows this is a pdf
        response.setContentType("application/pdf");
		//response.setHeader("extension", "pdf");
        //response.setHeader("Content-disposition", "attachment; filename=" + request.getServletPath().substring(request.getServletPath().lastIndexOf('/') + 1)+".pdf");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        if (sForm.getSimpleServiceOrderFl()) {
            PdfOrderStatus pdf = new PdfOrderStatus(request,localeCd);
            pdf.generatePdfforService(sForm, appUser, storeD, out, imgPath);
        } else {
            //---
            PdfOrderStatus pdf = null;
            AccountData accountD = appUser.getUserAccount();
            int accountId = accountD.getAccountId();

            String pdfClass = null;
            try {
              pdfClass = propEjb.getBusEntityProperty(accountId, RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_STATUS_CLASS);
            }
            catch (DataNotFoundException ex) {
              pdfClass = null;
            }
            catch (RemoteException ex) {
              pdfClass = null;
              ex.getStackTrace();
            }

            if(Utility.isSet(pdfClass)) {
                try {
                    Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pdfClass);
                    pdf = (PdfOrderStatus) clazz.newInstance();
                    Properties props = setSpecialPropertyForPrintPdf(accountId,factory, orderStatusDesc.getOrderDetail().getSiteId());
                    pdf.setMiscProperties(props);
                } catch (Exception exc) {
                    log.info("!!!!ATTENTION failed creating PdfOrderStatus using class name: "+pdfClass);
                }
            }
            if (pdf==null) {
                pdf = new PdfOrderStatus();
            }
            //----
            pdf.init(request, localeCd);
            pdf.generatePdf(sForm, appUser, storeD, out, imgPath);
        }
        response.setContentLength(out.size());
        out.writeTo(response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();

        return ae ;
    }


    public static ActionErrors consolidate(HttpServletRequest request, int pOrderId)
    throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();




        CleanwiseUser appUser = (CleanwiseUser)
                                 session.getAttribute(Constants.APP_USER);

        APIAccess factory = new APIAccess();
        Order orderEjb = factory.getOrderAPI();
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

        OrderJoinData ojD = orderEjb.fetchOrder(pOrderId);

        ShoppingCartData scD = (ShoppingCartData)
                                     session.getAttribute(Constants.SHOPPING_CART);


        UserData userD = appUser.getUser();
        SiteData siteD = appUser.getSite();
         //Verify shopping cart and create new one if necessary
         if ( scD == null || !(scD instanceof ConsolidatedCartView) ||
              scD.getUser()==null ||
              scD.getUser().getUserId()!=userD.getUserId() ||
              scD.getSite()==null ||
              scD.getSite().getBusEntity().getBusEntityId() !=
                                       siteD.getBusEntity().getBusEntityId()
            ) {
           scD = ConsolidatedCartView.createValue();
           scD.setUser(userD);
           scD.setSite(siteD);
         } else { //Check order duplication
           ArrayList orders = ((ConsolidatedCartView) scD).getOrders();
           for(Iterator iter = orders.iterator(); iter.hasNext();) {
             OrderJoinData cartOjD = (OrderJoinData) iter.next();
             if(cartOjD.getOrderId()==pOrderId) {
               String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.orderAlreadySelected",null);
               ae.add("error",new ActionError("error.simpleGenericError",errorMess));
               return ae;
             }
           }
         }


        //Verify items to add
        IdVector itemIdV = new IdVector();
        OrderItemJoinDataVector oijDV = ojD.getOrderJoinItems();
        for(Iterator iter = oijDV.iterator(); iter.hasNext();) {
          OrderItemJoinData oijD = (OrderItemJoinData) iter.next();
          OrderItemData oiD = oijD.getOrderItem();
          int itemId = oiD.getItemId();
          itemIdV.add(new Integer(itemId));
        }


        Integer catalogIdI = (Integer)session.getAttribute(Constants.CATALOG_ID);
        if(catalogIdI==null){
          String errorMess = "No "+Constants.CATALOG_ID+" session object";
          ae.add("error",new ActionError("error.systemError",errorMess));
          return ae;
       }


       int catalogId = catalogIdI.intValue();
       Integer contractIdI = (Integer)session.getAttribute(Constants.CONTRACT_ID);
       int contractId = 0;
       if(contractIdI!=null) {
         contractId = contractIdI.intValue();
       }

       String roleCd = userD.getUserRoleCd();
       if(roleCd==null) {
         roleCd = "";
       }
       boolean contractOnly =
        (roleCd.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY)>=0)?true:false;

       if(contractOnly && contractId==0) {
         String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noContractForSiteFound",null);
         ae.add("error",new ActionError("error.simpleGenericError",errorMess));
         return ae;
       }

       ShoppingCartItemDataVector sciDV = shoppingServEjb.prepareShoppingItems
          (appUser.getUserStore().getStoreType().getValue(), siteD,  catalogId, contractId, itemIdV,
          SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));

       //set quantities and check presents
       String missingSkus = null;
       for(Iterator iter = oijDV.iterator(); iter.hasNext();) {
         OrderItemJoinData oijD = (OrderItemJoinData) iter.next();
         OrderItemData oiD = oijD.getOrderItem();
         int itemId = oiD.getItemId();
         int qty = oiD.getTotalQuantityOrdered();
         boolean foundFl = false;
         for(Iterator iter1=sciDV.iterator(); iter1.hasNext();) {
           ShoppingCartItemData  wrkSciD = (ShoppingCartItemData) iter1.next();
           int iId = wrkSciD.getProduct().getProductId();
           if(iId==itemId) {
             if(contractOnly && !wrkSciD.getContractFlag()) {
               break;
             }
             foundFl=true;
             wrkSciD.setQuantity(qty);
             wrkSciD.setQuantityString(""+qty);
             break;
            }
         }
         if(!foundFl) {
           if(missingSkus==null) {
              missingSkus = ""+oiD.getItemSkuNum();
           } else {
             missingSkus += ", "+oiD.getItemSkuNum();
           }
         }
       }
       if(missingSkus!=null) {
         Object[] params = new Object[1];
         params[0] = missingSkus;
         String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.can'tFindSkus",params);
         ae.add("error",new ActionError("error.simpleGenericError",errorMess));
         return ae;
       }


       //Add items
       scD.addItems(sciDV);

       //Add order
       ArrayList orders =((ConsolidatedCartView) scD).getOrders();
       if(orders==null) {
          orders = new ArrayList();
          ((ConsolidatedCartView) scD).setOrders(orders);
       }
       orders.add(ojD);

       //Save shopping card as session attribute
       session.setAttribute(Constants.SHOPPING_CART,scD);
       //Init shopping cart form
       ShopTool.initShoppingForm(session);


    return ae;
  }

   public static ActionErrors deconsolidate(HttpServletRequest request, int pOrderId)
    throws Exception
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)
                                 session.getAttribute(Constants.APP_USER);
        String userName  = appUser.getUser().getUserName();

        APIAccess factory = new APIAccess();
        Order orderEjb = factory.getOrderAPI();
        orderEjb.deconsolidateOrder(pOrderId, userName);
    return ae;
  }


  public static ActionErrors goToOrderLocation(HttpServletRequest request, int pOrderId)
  throws Exception
  {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        Constants.APP_USER);
        int siteIdOld = appUser.getSite().getSiteId();

        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);
        Order orderBean = factory.getOrderAPI();
        Site siteEjb = factory.getSiteAPI();
        OrderJoinData ojd = orderBean.fetchOrder(pOrderId);

        int siteId = ojd.getOrder().getSiteId();
        SiteData sd = siteEjb.getSite(ojd.getOrder().getSiteId(), 0, false, SessionTool.getCategoryToCostCenterView(session, siteId) );
        int orderSiteId = sd.getBusEntity().getBusEntityId();
        LogOnLogic.siteShop(request, orderSiteId);
        if (siteIdOld != orderSiteId){
            refreshOGuides(request);
            UserShopLogic.init(request, (UserShopForm)session.getAttribute(Constants.USER_SHOP_FORM));
        }
        return ae;
  }

    public static ActionErrors addCustomerNote(HttpServletRequest request, int orderId)
    throws Exception{
        ActionErrors lUpdateErrors = new ActionErrors();
        String comment = request.getParameter("customerComment");
        if(!Utility.isSet(comment)) {
            return lUpdateErrors;
        }
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        if(appUser == null){
            lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,
                                  new ActionError("simple.generic.error", "Could not find user"));
            return lUpdateErrors;
        }

        if (comment.length() >= 250){
           lUpdateErrors.add("customerComment",
                                  new ActionError("variable.to.large.error", "comments"));
           return lUpdateErrors;
        }


        Order orderEjb = factory.getOrderAPI();
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setOrderId(orderId);
        opD.setValue(comment);
        opD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
        opD.setOrderPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);

        opD.setAddBy(appUser.getUserName());
        opD.setModBy(appUser.getUserName());
        opD = orderEjb.addNote(opD);

        return lUpdateErrors;
    }

    public static ActionErrors addComment(HttpServletRequest request, int pOrderId)
    throws Exception
    {
        ActionErrors ae = new ActionErrors();
        String comment = request.getParameter("customerComment");
        if(!Utility.isSet(comment)) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noCommentProvided",null);
            ae.add("error",
               new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        ae = addCustomerNote(request,pOrderId);
        if(ae.size()>0) {
            return ae;
        }
        viewOrder(request, pOrderId );

        return ae;
    }

    public ActionErrors validatePoNum (HttpServletRequest request, String pPoNum) {

  	  HttpSession session = request.getSession();
	  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	  String uniquePoReq =  appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.UNIQUE_PO_NUM_DAYS);

	  ActionErrors ae = new ActionErrors();
	  try{
		  if(Utility.isSet(uniquePoReq)){
			  int uniquePoDays = Integer.parseInt(uniquePoReq);
			  //this account requiers a unique po number.  This will be validated against the database
			  if(uniquePoDays > 0 && Utility.isSet(pPoNum)){
				  Date fromDate = Utility.addDays(new Date(), uniquePoDays * -1);
				  APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
				  OrderDataVector orders = factory.getOrderAPI().getOrderByCustomerPoNumber(pPoNum,appUser.getUserAccount().getAccountId(),fromDate);
				  if(orders != null && orders.size() > 0){
					  String oidstr = (String) request.getParameter("orderId");
					  if(Utility.isSet(oidstr)){
						  	int oid = Integer.parseInt(oidstr);
						  	Iterator it = orders.iterator();
						  	while(it.hasNext()){
						  		OrderData od = (OrderData) it.next();
						  		if(od.getOrderId() == oid){
						  			it.remove();
						  		}
						  	}
					  }
					  if(orders.size() > 0){
						  String errorMess =ClwI18nUtil.getMessage(request, "shop.errors.poNumberMustBeUnique", null);
					      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
					  }
				  }
			  }
		  }
	  }catch(Exception e){
		  log.error(e);
	  }
	  return ae;

    }


}

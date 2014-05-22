package com.cleanwise.view.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.logic.ServiceProviderMgrLogic;
import com.cleanwise.view.logic.UserWorkOrderSchedulerLogic;

/**
 * Title:        ServiceProviderMgrAction
 * Description:  Actions manager for the work order processing.
 * Purpose:      Class for calling logic methods  which  process the request.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         10.10.2007
 * Time:         15:29:52
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class ServiceProviderMgrAction extends ActionSuper {

    private static final String FAILURE = "failure";
    private static final String MAIN    = "main";
    private static final String SUCCESS = "success";
    private static final String DISPLAY = "display";
    private static final String ERROR   = "error";
    private static final String SEARCH  = "search";
    private static final String DETAIL  = "detail";
    private static final String SHOP_FORWARD  = "shopForward";
    private static final String CONTENT_DETAIL = "contentDetail";
    private static final String PARTS_ORDER = "partsOrder";
    private static final String CONTACT_US = "contactUs";
    private static final String COMPLETE = "complete";
    private static final String SUCCESS_ITEM = "successItem";
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @param form     Description of Parameter
     * @return Description of the Returned Value
     * @throws java.io.IOException            if an input/output error occurs
     * @throws javax.servlet.ServletException if a servlet exception occurs
     */
    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException, ServletException {

        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }

        SessionTool st = new SessionTool(request);
        if (!st.checkSession()) {
            logm("session timeout " + st.paramDebugString());
            return mapping.findForward(st.getLogonMapping());
        }

        ActionForward actionForward;
        try {
            actionForward = runWorkerForm(action, form, request, mapping, response);
        } catch (Exception e) {
            e.printStackTrace();
            actionForward = mapping.findForward(FAILURE);
        }

        navigateBreadCrumb(request, actionForward);

        return actionForward;
    }

    private ActionForward runWorkerForm(String action, ActionForm form, HttpServletRequest request, ActionMapping mapping, HttpServletResponse response) {

        String forward_page = MAIN;
        logm("action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        try {
            if (form instanceof UserWorkOrderMgrForm) forward_page = userWorkOrderMgrFormWorker(action, form, request,response);
            else if (form instanceof UserWorkOrderDetailMgrForm) forward_page = userWorkOrderDetailMgrFormWorker(action, form, request,response);
            else if (form instanceof UserWorkOrderItemMgrForm) forward_page = userWorkOrderItemMgrFormWorker(action, form, request,response);
            else if (form instanceof UserWorkOrderNoteMgrForm) forward_page = userWorkOrderNoteMgrFormWorker(action, form, request);
            else if (form instanceof UserWorkOrderContentMgrForm) forward_page = userWorkOrderContentMgrFormWorker(action, form, request, response);
            else if (form instanceof UserWorkOrderSchedulerForm) forward_page = userWorkOrderSchedulerFormWorker(action, form, request, response);
            else logm("The worker of the form can't be found.Unknown form : " + form);
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward(ERROR);
        }

        if(forward_page==null){
            return null;
        }

        logm("Forward page :" + mapping.findForward(forward_page).getPath());
        return mapping.findForward(forward_page);

    }

    private String userWorkOrderSchedulerFormWorker(String action,
                                                    ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        String init   = "init";
        String save   = "saveSchedule";
        String detail = "detail";
        String delete = "deleteSchedule";
        String navigateForward  = "navigateForward";
        String navigateBackward = "navigateBackward";
        String typeChange = "scheduleTypeChange";

        if (action.equals(init)) {
            ActionErrors ae = UserWorkOrderSchedulerLogic.init(request, (UserWorkOrderSchedulerForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(save)) {
            ActionErrors ae = UserWorkOrderSchedulerLogic.saveSchedule(request, (UserWorkOrderSchedulerForm)form);
            if(ae.size()>0) {
                saveErrors(request,ae);
                return FAILURE;
            }
        } else if (action.equals(detail)) {
            ActionErrors ae = UserWorkOrderSchedulerLogic.getDetail(request, (UserWorkOrderSchedulerForm)form);
            if(ae.size()>0) {
                saveErrors(request,ae);
                return FAILURE;
            }
            return SUCCESS;
        }  else if (action.equals(delete)) {
            ActionErrors ae = UserWorkOrderSchedulerLogic.deleteSchedule(request, (UserWorkOrderSchedulerForm)form);
            if(ae.size()>0) {
                saveErrors(request,ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (navigateForward.equals(action)) {
            ActionErrors ae = UserWorkOrderSchedulerLogic.navigateForward(request,(UserWorkOrderSchedulerForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (navigateBackward.equals(action)) {
            ActionErrors ae = UserWorkOrderSchedulerLogic.navigateBackward(request, (UserWorkOrderSchedulerForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (typeChange.equals(action)) {
            ActionErrors ae = UserWorkOrderSchedulerLogic.scheduleTypeChange(request, (UserWorkOrderSchedulerForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }

        return SUCCESS;
    }

    private String userWorkOrderContentMgrFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String createStr     = "create";
        String saveStr       = "save";
        String init          = "init";
        String contentDetail = "contentDetail";
        String readDoc       = "readDoc";
        String removeStr     = "remove";

        if (action.equals(contentDetail)) {
            ActionErrors ae = ServiceProviderMgrLogic.getWorkOrderContentDetail(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.updateWorkOrderContent(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return DETAIL;
        } else if (action.equals(readDoc)) {
            ActionErrors ae = ServiceProviderMgrLogic.readDocument(request, (UserWorkOrderContentMgrForm) form, response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrderContent(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }  else if (action.equals(removeStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.removeWorkOrderContent(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }  else if (action.equals(init)) {
            ActionErrors ae = ServiceProviderMgrLogic.init(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return DISPLAY;
    }

    private String userWorkOrderNoteMgrFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        String createStr  = "create";
        String saveStr    = "save";
        String init       = "init";
        String noteDetail = "noteDetail";

        if (action.equals(noteDetail)) {
            ActionErrors ae = ServiceProviderMgrLogic.getNoteDetail(request, (UserWorkOrderNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.updateWorkOrderNote(request, (UserWorkOrderNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrderNote(request, (UserWorkOrderNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(init)) {
            ActionErrors ae = ServiceProviderMgrLogic.init(request, (UserWorkOrderNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return DISPLAY;
    }

    private String userWorkOrderItemMgrFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageResources mr = getResources(request);

        String createStr = "create";
        String saveStr   = "save";
        String init      = "init";
        String detail    = "detail";
        String downSeq   = "downSeq";
        String upSeq     = "upSeq";
        String createContent = "createContent";
        String removeStr     = "remove";
        String changeActiveAsset = "changeActiveAsset";
        String orderPartsStr = "orderParts";

        if (action.equals(detail)) {
            ActionErrors ae = ServiceProviderMgrLogic.getDetail(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.updateWorkOrderItem(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(changeActiveAsset)) {
            ActionErrors ae = ServiceProviderMgrLogic.changeActiveAsset(request, (UserWorkOrderItemMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return null;
        } else if (action.equals(createContent)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrderContent(request, (UserWorkOrderContentMgrForm)null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return CONTENT_DETAIL;
        } else if (action.equals(createStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrderItem(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(init)) {
            ActionErrors ae = ServiceProviderMgrLogic.init(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(downSeq)) {
            ActionErrors ae = ServiceProviderMgrLogic.downItemSequence(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(upSeq)) {
            ActionErrors ae = ServiceProviderMgrLogic.upItemSequence(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(removeStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.removeWorkOrderItem(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(orderPartsStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.orderPartsForWorkOrderItem(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SHOP_FORWARD;
        }
        return DISPLAY;
    }

    private String userWorkOrderDetailMgrFormWorker(String action, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {

        String createStr             = "create";
        String saveStr               = "save";
        String cancel                = "cancel";
        String init                  = "init";
        String detail                = "detail";
        String createNote            = "createNote";
        String createItem            = "createItem";
        String createContent         = "createContent";
        String warrantyInfo          = "getWarrantyInfo";
        String print                 = "print";
        String changeServiceProvider = "changeServiceProvider";
        String changeWorkOrderType   = "changeWorkOrderType";
        String sendPdfToProvider     = "sendPdfToProvider";
        String changeActiveAsset     = "changeActiveAsset";
        String addTableLines         = "addItemizedServiceTableLines";
        String completeStr           = "complete";
        String acceptStr             = "accept";
        String rejectStr             = "reject";
        String removeStr                = "remove";

        if (action.equals(detail)) {
            ActionErrors ae = ServiceProviderMgrLogic.getDetail(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createContent)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrderContent(request, (UserWorkOrderContentMgrForm)null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return CONTENT_DETAIL;
        }  else if (action.equals(changeServiceProvider)) {
            ActionErrors ae = ServiceProviderMgrLogic.changeServiceProvider(request, (UserWorkOrderDetailMgrForm)form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return null;
        } else if (action.equals(createItem)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrderItem(request, (UserWorkOrderItemMgrForm) null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(createNote)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrderNote(request, (UserWorkOrderNoteMgrForm) null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.updateWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }  else if (action.equals(warrantyInfo)) {
            ActionErrors ae = ServiceProviderMgrLogic.getWarrantyInfo(request, (UserWorkOrderDetailMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return null;
        } else if (action.equals(createStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrder(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(init)) {
            ActionErrors ae = ServiceProviderMgrLogic.init(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(changeWorkOrderType)) {
            ActionErrors ae = ServiceProviderMgrLogic.changeWorkOrderType(request, (UserWorkOrderDetailMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return null;
        } else if (action.equals(sendPdfToProvider)) {
            ActionErrors ae = ServiceProviderMgrLogic.sendPdfToProvider(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(print)) {
            ActionErrors ae = ServiceProviderMgrLogic.print(request, (UserWorkOrderDetailMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(cancel)) {
            ActionErrors ae = ServiceProviderMgrLogic.cancelWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(completeStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.completeWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(changeActiveAsset)) {
            ActionErrors ae = ServiceProviderMgrLogic.changeActiveAsset(request, (UserWorkOrderDetailMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return null;
        } else if (action.equals(addTableLines)) {
            ActionErrors ae = ServiceProviderMgrLogic.addTableLines(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(acceptStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.acceptWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return COMPLETE;
        } else if (action.equals(rejectStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.rejectWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return COMPLETE;
        }
        return SUCCESS;
    }

    private String userWorkOrderMgrFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //MessageResources mr = getResources(request);
        //String searchStr = getMessage(mr, request, "global.action.label.search");
        String workOrderSearch = "WorkOrderSearch";
        String init = "init";
        String searchByPriority = "searchByPriority";
        String createStr = "create";
        String createWorkOrderFromTemplate ="createWorkOrderFromTemplate";
        String searchPendingOrders = "searchPendingOrders";
        String contactUsStr = "contactUs";
        String startSearchStr = "startSearch";
        String sortWorkOrders = "sort_workorders";
        String locateSite = "Locate Site";
        String searchProviderSiteLocate = "ServiceProviderSiteLocate";
        String cancel = "Cancel";
        String returnSelected = "Return Selected";
        String clearSiteFilter = "Clear Site Filter";

       if (action.equals(workOrderSearch)) {
            ActionErrors ae = ServiceProviderMgrLogic.search(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (action.equals(init)) {
            ActionErrors ae = ServiceProviderMgrLogic.initSearch(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(startSearchStr)) {
            if (request.getSession().getAttribute(ServiceProviderMgrLogic.USER_WORK_ORDER_MGR_FORM) == null) {
                ActionErrors ae = ServiceProviderMgrLogic.initSearch(request, (UserWorkOrderMgrForm) form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                return FAILURE;
                }
            }
            return SEARCH;
        } else if (action.equals(createWorkOrderFromTemplate)) {
            ActionErrors ae = ServiceProviderMgrLogic.createWorkOrderFromTemplate(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }  else if (action.equals(createStr)) {
            ActionErrors ae = ServiceProviderMgrLogic.createNewWorkOrder(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(searchByPriority)) {
            ActionErrors ae = ServiceProviderMgrLogic.searchByPriority(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (action.equals(searchPendingOrders)) {
            ActionErrors ae = ServiceProviderMgrLogic.searchPendingOrders(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(sortWorkOrders)) {
            ActionErrors ae = ServiceProviderMgrLogic.sort(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (action.equals(locateSite)) {
            return SEARCH;
        } else if (action.equals(searchProviderSiteLocate)) {
            return SEARCH;
        } else if (action.equals(cancel)) {
            return SEARCH;
        } else if (action.equals(returnSelected)) {
            return SEARCH;
        } else if (action.equals(clearSiteFilter)) {
            return SEARCH;
        } else if (action.equals(contactUsStr)) {
            return CONTACT_US;
        }
        return DISPLAY;
    }
}

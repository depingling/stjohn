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
import com.cleanwise.view.logic.UserWorkOrderMgrLogic;
import com.cleanwise.view.logic.UserWorkOrderSchedulerLogic;

/**
 * Title:        UserWorkOrderMgrAction
 * Description:  Actions manager for the work order processing.
 * Purpose:      Class for calling logic methods  which  process the request.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         10.10.2007
 * Time:         15:29:52
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWorkOrderMgrAction extends ActionSuper {

    private static final String FAILURE = "failure";
    private static final String MAIN    = "main";
    private static final String SUCCESS = "success";
    private static final String DISPLAY = "display";
    private static final String ERROR   = "error";
    private static final String SEARCH  = "search";
    private static final String DETAIL  = "detail";
    private static final String SHOP_FORWARD  = "shopForward";
    private static final String CONTENT_DETAIL = "contentDetail";
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
            ActionErrors ae = UserWorkOrderMgrLogic.getWorkOrderContentDetail(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return DETAIL;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.updateWorkOrderContent(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return DETAIL;
        } else if (action.equals(readDoc)) {
            ActionErrors ae = UserWorkOrderMgrLogic.readDocument(request, (UserWorkOrderContentMgrForm) form, response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrderContent(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }  else if (action.equals(removeStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.removeWorkOrderContent(request, (UserWorkOrderContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return MAIN;
        }  else if (action.equals(init)) {
            ActionErrors ae = UserWorkOrderMgrLogic.init(request, (UserWorkOrderContentMgrForm) form);
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
            ActionErrors ae = UserWorkOrderMgrLogic.getNoteDetail(request, (UserWorkOrderNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.updateWorkOrderNote(request, (UserWorkOrderNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(createStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrderNote(request, (UserWorkOrderNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(init)) {
            ActionErrors ae = UserWorkOrderMgrLogic.init(request, (UserWorkOrderNoteMgrForm) form);
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
            ActionErrors ae = UserWorkOrderMgrLogic.getDetail(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.updateWorkOrderItem(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(changeActiveAsset)) {
            ActionErrors ae = UserWorkOrderMgrLogic.changeActiveAsset(request, (UserWorkOrderItemMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return null;
        } else if (action.equals(createContent)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrderContent(request, (UserWorkOrderContentMgrForm)null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return CONTENT_DETAIL;
        } else if (action.equals(createStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrderItem(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(init)) {
            ActionErrors ae = UserWorkOrderMgrLogic.init(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(downSeq)) {
            ActionErrors ae = UserWorkOrderMgrLogic.downItemSequence(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(upSeq)) {
            ActionErrors ae = UserWorkOrderMgrLogic.upItemSequence(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(removeStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.removeWorkOrderItem(request, (UserWorkOrderItemMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(orderPartsStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.orderPartsForWorkOrderItem(request, (UserWorkOrderItemMgrForm) form);
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
        String copyStr               = "copy";
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
        String complete              = "complete";
        String remove                = "remove";
        String orderParts            = "orderParts";

        if (action.equals(detail)) {
            ActionErrors ae = UserWorkOrderMgrLogic.getDetail(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createContent)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrderContent(request, (UserWorkOrderContentMgrForm)null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return CONTENT_DETAIL;
        }  else if (action.equals(changeServiceProvider)) {
            ActionErrors ae = UserWorkOrderMgrLogic.changeServiceProvider(request, (UserWorkOrderDetailMgrForm)form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return null;
        } else if (action.equals(createItem)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrderItem(request, (UserWorkOrderDetailMgrForm) null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(createNote)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrderNote(request, (UserWorkOrderNoteMgrForm) null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.updateWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }  else if (action.equals(warrantyInfo)) {
            ActionErrors ae = UserWorkOrderMgrLogic.getWarrantyInfo(request, (UserWorkOrderDetailMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return null;
        } else if (action.equals(createStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrder(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(init)) {
            ActionErrors ae = UserWorkOrderMgrLogic.init(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(changeWorkOrderType)) {
            ActionErrors ae = UserWorkOrderMgrLogic.changeWorkOrderType(request, (UserWorkOrderDetailMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return null;
        } else if (action.equals(sendPdfToProvider)) {
            ActionErrors ae = UserWorkOrderMgrLogic.sendPdfToProvider(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(print)) {
            ActionErrors ae = UserWorkOrderMgrLogic.print(request, (UserWorkOrderDetailMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(cancel)) {
            ActionErrors ae = UserWorkOrderMgrLogic.cancelWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(complete)) {
            ActionErrors ae = UserWorkOrderMgrLogic.completeWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(changeActiveAsset)) {
            ActionErrors ae = UserWorkOrderMgrLogic.changeActiveAsset(request, (UserWorkOrderDetailMgrForm) form,response);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return null;
        } else if (action.equals(addTableLines)) {
            ActionErrors ae = UserWorkOrderMgrLogic.addTableLines(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(remove)) {
            ActionErrors ae = UserWorkOrderMgrLogic.removeWorkOrderItem(request, (UserWorkOrderDetailMgrForm)form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(copyStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.copyWorkOrder(request, (UserWorkOrderDetailMgrForm)form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(orderParts)) {
            ActionErrors ae = UserWorkOrderMgrLogic.orderPartsForWorkOrder(request, (UserWorkOrderDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SHOP_FORWARD;
        }
        return SUCCESS;
    }

    private String userWorkOrderMgrFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr, request, "global.action.label.search");
        String init = "init";
        String searchByPriority = "searchByPriority";
        String createStr = "create";
        String createWorkOrderFromTemplate ="createWorkOrderFromTemplate";
        String searchPendingOrders = "searchPendingOrders";
        String sortWorkOrders = "sort_workorders";
        
       if (action.equals(searchStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.search(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (action.equals(init)) {
            ActionErrors ae = UserWorkOrderMgrLogic.initSearch(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (action.equals(createWorkOrderFromTemplate)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createWorkOrderFromTemplate(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }  else if (action.equals(createStr)) {
            ActionErrors ae = UserWorkOrderMgrLogic.createNewWorkOrder(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(searchByPriority)) {
            ActionErrors ae = UserWorkOrderMgrLogic.searchByPriority(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (action.equals(searchPendingOrders)) {
            ActionErrors ae = UserWorkOrderMgrLogic.searchPendingOrders(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(sortWorkOrders)) {
            ActionErrors ae = UserWorkOrderMgrLogic.sort(request, (UserWorkOrderMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return DISPLAY;
    }
}

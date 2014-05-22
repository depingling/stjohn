package com.cleanwise.view.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.logic.EventAdmMgrLogic;

public class EventAdmMgrAction extends ActionSuper {

    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        String func = request.getParameter("func");
        String successForward = "init";
        String failForward = "init";

        if (action == null) {
            action = "init";
        }
        if (func == null) {
            func = "";
        }
        try {
            ActionErrors errors = new ActionErrors();
            if (action.equals("update")) {
                successForward = "groupdetail";
                failForward = "groupdetail";
            } else if (action.equals("search")) {
                successForward = "search";
                failForward = "search";
                errors = new ActionErrors();
                if ("stop".equals(func)) {
                    errors.add(EventAdmMgrLogic.stop(response, request, form));
                } else if ("delete".equals(func)) {
                    errors.add(EventAdmMgrLogic.delete(response, request,
                                    form));
                } else if ("process_event".equals(func)) {
                    errors.add(EventAdmMgrLogic.processEvent(response, request,
                                    form));
                } else if ("set_ready".equals(func)) {
                    errors.add(EventAdmMgrLogic.setEventStatus(request,
                    		RefCodeNames.EVENT_STATUS_CD.STATUS_READY));
                }
                errors.add(EventAdmMgrLogic.search(response, request, form));
            } else if (action.equals("edit")) {
                successForward = "edit";
                failForward = "edit";
                if ("create".equals(func)) {
                    successForward = "edit";
                    failForward = "creating";
                    errors = EventAdmMgrLogic.creating(response, request, form);
                }
                if (errors.size() == 0) {
                    errors = EventAdmMgrLogic.edit(response, request, form);
                }
                if ("download".equals(func)
                        && (errors == null || errors.size() == 0)) {
                    return null;
                }
            } else if (action.equals("detail")) {
                successForward = "groupdetail";
                failForward = "groupdetail";
            } else if (action.equals("creating")) {
                successForward = "creating";
                failForward = "creating";
                errors = EventAdmMgrLogic.initCreating(response, request, form);
            } else if (action.equals("upload")) {
                errors = EventAdmMgrLogic.upload(response, request, form);
            } else if (action.equals("control")) {
              successForward = "control";
              failForward = "control";
              EventAdmMgrLogic.eventSystemControl(form, func);
            } else if (action.equals("changeProccessLog")) {
                EventAdmMgrLogic.changeProcessLog(request,response,form);
                return null;
            } else if (action.equals("init")) {
                errors = EventAdmMgrLogic.initStatistic(response, request, form);
            }
            EventAdmMgrLogic.init(response, request, form);
            if (errors.size() > 0) {
                saveErrors(request, errors);
                return (mapping.findForward(failForward));
            } else {
                return (mapping.findForward(successForward));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward(failForward));
        }
    }
}

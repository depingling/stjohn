package com.cleanwise.view.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleanwise.view.logic.ProcessAdmMgrLogic;


public class ProcessAdmMgrAction extends ActionSuper {

    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {


        try {

            String action = request.getParameter("action");
            if (action == null) {
                action = "init";
            }
            if ("init".equals(action)) {
                ActionErrors ae = ProcessAdmMgrLogic.init(request, form);
                if (ae.size() > 0) { saveErrors(request, ae); }
                return mapping.findForward("init");

            } else if ("creating".equals(action)) {
                ActionErrors ae = ProcessAdmMgrLogic.createNewTemplateProcess(request, form);
                if (ae.size() > 0) { saveErrors(request, ae); }
                return mapping.findForward("creating");

            } else if ("Save".equals(action)) {
                ActionErrors ae = ProcessAdmMgrLogic.saveNewTemplateProcess(request, form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("creating");
            }
             else if ("catalog".equals(action)) {
                ActionErrors ae = ProcessAdmMgrLogic.initDetails(request, form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
             else if ("changeCurrentTemplateProcess".equals(action)) {
                ActionErrors ae = ProcessAdmMgrLogic.getDetails(request, form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
            else if("Save All".equals(action)){
                ActionErrors ae = ProcessAdmMgrLogic.saveDetails(request, form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
             else if("Add New Task".equals(action)){
                ActionErrors ae = ProcessAdmMgrLogic.addNewTask(request, form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
            else if("addOutputParam".equals(action)){
                ActionErrors ae = ProcessAdmMgrLogic.addOutputTaskProperty(request,form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
             else if("addInputParam".equals(action)){
                ActionErrors ae = ProcessAdmMgrLogic.addInputTaskProperty(request,form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
             else if("addUpLink".equals(action)){
                ActionErrors ae = ProcessAdmMgrLogic.addTaskWithDownLink(request,form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
            else if("addDownLink".equals(action)){
                ActionErrors ae = ProcessAdmMgrLogic.addTaskWithUpLink(request,form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
            else if("deleteProps".equals(action)){
                ActionErrors ae = ProcessAdmMgrLogic.deleteProps(request,form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
            else if("deleteTask".equals(action)){
                ActionErrors ae = ProcessAdmMgrLogic.deleteTask(request,form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
            return (mapping.findForward("init"));

        } catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }
    }
}

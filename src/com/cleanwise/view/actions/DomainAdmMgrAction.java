package com.cleanwise.view.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleanwise.view.logic.DomainAdmMgrLogic;
import com.cleanwise.view.logic.DomainConfigurationLogic;


public class DomainAdmMgrAction extends ActionSuper {

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
                ActionErrors ae = DomainAdmMgrLogic.init(request, form);
                if (ae.size() > 0) { saveErrors(request, ae); }
                return mapping.findForward("init");

            } else if ("createDomain".equals(action)) {
                ActionErrors ae = DomainAdmMgrLogic.createNewDomain(request, form);
                if (ae.size() > 0) { saveErrors(request, ae); }
                return mapping.findForward("detail");

            } else if ("saveDomain".equals(action)) {
                ActionErrors ae = DomainAdmMgrLogic.saveNewDomain(request, form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
             else if ("detail".equals(action)) {
                ActionErrors ae = DomainAdmMgrLogic.initDetails(request, form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("detail");
            }
             else if ("configuration".equals(action)) {
                ActionErrors ae = DomainConfigurationLogic.configurateDomain(request, form);
                if (ae.size() > 0) { saveErrors(request, ae);  }
                return mapping.findForward("configuration");
            }
//            else if("Save All".equals(action)){
//                ActionErrors ae = DomainAdmMgrLogic.saveDetails(request, form);
//                if (ae.size() > 0) { saveErrors(request, ae);  }
//                return mapping.findForward("detail");
//            }
            return (mapping.findForward("init"));

        } catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }
    }
}

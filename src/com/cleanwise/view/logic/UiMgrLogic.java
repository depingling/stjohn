package com.cleanwise.view.logic;

import com.cleanwise.view.forms.UiMgrForm;
import com.cleanwise.view.utils.UiConfigContext;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.log4j.Logger;



public class UiMgrLogic {

    private static final Logger log = Logger.getLogger(UiMgrLogic.class);
    private static final String UI_MGR_FORM = "UI_MGR_FORM";

    public static ActionErrors init(UiMgrForm pForm, HttpServletRequest request) {

        log.info("init => BEGIN.pForm "+pForm);

        pForm = new UiMgrForm();

        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        session.setAttribute(UI_MGR_FORM, pForm);

        log.info("init => END. Error Size : " + ae.size());

        return ae;
    }


     private static ActionErrors checkRequest(ActionForm pForm, HttpServletRequest request) {

        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }

        return ae;

    }

    private static ActionErrors checkRequest(HttpServletRequest request) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        UiConfigContext context = (UiConfigContext) request.getSession().getAttribute(Constants.UI_CONFIG_CONTEXT);
        if (context == null) {
            ae.add("error", new ActionError("error.systemError", "No ui context info"));
            return ae;
        }

        if (context.getManagedGroup() == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No managed group"));
            return ae;
        }

        return ae;
    }

    public static ActionErrors detail(UiMgrForm pForm, HttpServletRequest request) throws Exception {

            log.info("detail => BEGIN");

            ActionErrors ae = checkRequest(pForm,request);
             if (ae.size() > 0) {
                 return ae;
             }

             HttpSession session = request.getSession();
             APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
             Ui uiEjb = factory.getUiAPI();

             UiConfigContext context = (UiConfigContext) request.getSession().getAttribute(Constants.UI_CONFIG_CONTEXT);
             GroupData group = context.getManagedGroup();

             UiView ui = null;
             try {
                 ui = uiEjb.getUiForGroup(group.getGroupId());
             } catch (Exception e) {
                 e.printStackTrace();
             }


        if(ui == null)  {
           ui =  createNewUi();
        }

        pForm.setUiView(ui);
        session.setAttribute(UI_MGR_FORM, pForm);
         
        log.info("detail => END");

        return ae;

    }

    private static UiView createNewUi() {

        UiView uiView = new UiView();

        uiView.setUiData(UiData.createValue());
        uiView.setUiAssociations(new UiAssocDataVector());
        uiView.setUiPages(new UiPageViewVector());

        return uiView;

    }

    public static ActionErrors save(UiMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkFormAttr(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        Ui uiEjb = factory.getUiAPI();

        UiConfigContext context = (UiConfigContext) request.getSession().getAttribute(Constants.UI_CONFIG_CONTEXT);
        GroupData group = context.getManagedGroup();

        if (isNew(pForm.getUiView())) {
            createAssocitions(pForm.getUiView(), group);
        }

        uiEjb.saveUi(pForm.getUiView(), appUser.getUserName());


        return detail(pForm, request);

    }

    private static void createAssocitions(UiView uiView, GroupData group) {

        UiAssocData uiAssocData = UiAssocData.createValue();
        uiAssocData.setGroupId(group.getGroupId());
        uiView.getUiAssociations().add(uiAssocData);

    }

    private static boolean isNew(UiView pUiView) {
        return !(pUiView.getUiData().getUiId() > 0);
    }

    private static ActionErrors checkFormAttr(UiMgrForm pForm, HttpServletRequest request) {

        ActionErrors ae = new ActionErrors();

        if (!Utility.isSet(pForm.getUiView().getUiData().getShortDesc())) {
            ae.add("Ui Name", new ActionError("variable.empty.error", "Ui Name"));
        }

        if (!Utility.isSet(pForm.getUiView().getUiData().getStatusCd())) {
            ae.add("Status Code", new ActionError("variable.empty.error", "Status Code"));
        }

        return ae;
    }


}

package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.forms.FAQForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.value.NoteJoinViewVector;
import javax.servlet.http.HttpSession;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.session.Note;
import com.cleanwise.service.api.util.DBCriteria;
import java.util.List;

public final class FAQAction extends ActionSuper {
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping
     *            The ActionMapping used to select this instance
     * @param request
     *            The HTTP request we are processing
     * @param response
     *            The HTTP response we are creating
     * @param form
     *            Description of Parameter
     * @return Description of the Returned Value
     * @exception IOException
     *                if an input/output error occurs
     * @exception ServletException
     *                if a servlet exception occurs
     */
    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = (String) request.getParameter("action");
        if (action == null || action.compareTo("") == 0) {
            action = "init";
        }
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false) {
            return mapping.findForward("/userportal/logon");
        }
        action = action.toLowerCase();
        FAQForm theForm = (FAQForm) form;
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();
        try {
            ActionErrors errors = new ActionErrors();
            APIAccess factory = new APIAccess();
            /**-----------------  FAQLogic ------------- */
            NoteJoinViewVector faqViewV = null;
            Note noteBean = factory.getNoteAPI();

            //Create criteria for searching
            DBCriteria dbCrit = new DBCriteria();
            try {
              faqViewV = noteBean.getNotesForUser(appUser.getUser().getUserId(), dbCrit, "FAQ", storeId);
              if(faqViewV == null){
                errors.add("error", new ActionError("error.systemError", "No FAQs "));
              }
              //--- filter for user's locale -----
              faqViewV = (NoteJoinViewVector)noteBean.findNotesByLocale(faqViewV, appUser.getUser().getPrefLocaleCd());
//              faqViewV = new NoteJoinViewVector();
//              for (int i = 0; i < dV.size(); i++) {
//                faqViewV.add(dV.get(i));
//              }
              //----------------------------------
              theForm.setFaqViewVector(faqViewV);
            }
            catch (Exception exc) {
              exc.printStackTrace();
              errors.add("error", new ActionError("error.systemError", exc.getMessage()));
            }
            /*----------------------------------------*/
            return mapping.findForward("display");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward("error");
        }
    }

}

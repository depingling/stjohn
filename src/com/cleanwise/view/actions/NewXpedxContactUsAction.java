package com.cleanwise.view.actions;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

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
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.forms.NewXpedxContactUsForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.UserMgrLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.validators.EmailValidator;

public final class NewXpedxContactUsAction extends ActionSuper {
    private final static String EMAIL_DELIMITER = "#";

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
        NewXpedxContactUsForm bForm = (NewXpedxContactUsForm) form;
        try {
            ActionErrors errors = new ActionErrors();
            if (action.compareTo("send_email") == 0) {
                processEmailForm(request, bForm, errors);
                if (errors.size() > 0) {
                    saveErrors(request, errors);
                } else {
                    request.setAttribute("successMessage", ClwI18nUtil
                            .getMessage(request, "newxpdex.contactUs."
                                    + "success.submitted", null));
                }
            }
            if (errors.size() == 0) {
                String uids = (String) request.getSession().getAttribute(
                        Constants.USER_ID);
                int iuser = Utility.parseInt(uids);
                UserInfoData userInfo = UserMgrLogic.getUserDetailById(request,
                        iuser);
                bForm.setFrom(userInfo.getUserData().getFirstName() + " " + userInfo.getUserData().getLastName());
                bForm.setEmail(userInfo.getEmailData().getEmailAddress());
                bForm.setPhone(userInfo.getPhone().getPhoneNum());
                bForm.setOrder("");
                bForm.setTopic("");
                bForm.setComment("");
            }
            APIAccess factory = new APIAccess();
            PropertyService ps = factory.getPropertyServiceAPI();
            SessionTool sessionTool = new SessionTool(request);
            CleanwiseUser appUser = sessionTool.getUserData();
            int accountId = appUser.getSite().getAccountId();
            /*PropertyDataVector contactUsTopics = ps.getProperties(null,
                    new Integer(accountId),
                    RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TOPIC);*/
            PropertyDataVector contactUsTopics = ps.getPropertiesForLocale(appUser.getUser(),
                    new Integer(accountId),
                    RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TOPIC);
            Collections.sort(contactUsTopics, new Comparator() {
                public int compare(Object o1, Object o2) {
                    PropertyData pd1 = (PropertyData) o1;
                    PropertyData pd2 = (PropertyData) o2;
                    if (pd1 != null && Utility.isSet(pd1.getValue()) && 
                    	pd2 != null && Utility.isSet(pd2.getValue())) {
                        return pd1.getValue().compareToIgnoreCase(
                                pd2.getValue());
                    }
                    return 0;
                }
            });
            excludeEmail(contactUsTopics);
            request.setAttribute("contact.us.topics", contactUsTopics);
            return mapping.findForward("display");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward("error");
        }
    }

    private void processEmailForm(HttpServletRequest request,
            NewXpedxContactUsForm form, ActionErrors errors) throws Exception {
        if (Utility.isSet(form.getFrom()) == false
                || Utility.isSet(form.getEmail()) == false
                || Utility.isSet(form.getTopic()) == false) {
            errors.add("error", new ActionError("error.simpleGenericError",
                    ClwI18nUtil.getMessage(request,
                            "newxpdex.userProfile.errors."
                                    + "changePassword.needEnterRequiredFields",
                            null)));
        }
      //STJ - 3846
        form.setEmail(form.getEmail().trim());
        EmailValidator.validateEmail(request, errors,
                "newxpdex.contactUs.label.email", null, form.getEmail());
        if (errors.size() > 0) {
            return;
        }
        APIAccess factory = new APIAccess();
        EmailClient emc = factory.getEmailClientAPI();
        PropertyService ps = factory.getPropertyServiceAPI();
        String toEmail = null;
        String subj = null;
        String siteName="";
        String ccEmail = null;

        int topicId = Utility.parseInt(form.getTopic());
        if (topicId > 0) {
            PropertyData topic = ps.getProperty(topicId);
            if (topic != null) {
                subj = topic.getValue();
                if (subj != null) {
                    int index = subj.lastIndexOf(EMAIL_DELIMITER);
                    if (index > 0) {
                        toEmail = subj.substring(index + 1, subj.length());
                        subj = subj.substring(0, index);
                    }
                } else {
                    subj = "TOPIC'S VALUE IS NULL FOR PROPERTY ID:"
                            + form.getTopic();
                }
            } else {
                subj = "NOT FOUND TOPIC FOR PROPERTY ID:" + form.getTopic();
            }
        }
         CleanwiseUser appUser = (CleanwiseUser) request.getSession()
         .getAttribute(Constants.APP_USER);
         if (appUser != null && appUser.getSite() != null) {
        	 siteName = appUser.getSite().getBusEntity().getShortDesc();

        	 AccountData acc = appUser.getUserAccount();
        	 ccEmail = acc.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_CC_ADD);

         }

        if (toEmail == null) {
            toEmail = emc.getDefaultEmailAddress();
            subj = "NO STORE EMAIL FOUND FOR MESSAGE: " + subj;
        }
        String responseSubj = "ORDERLINE SYSTEM:  "+siteName+" - Contact Us Online Response";

        StringBuffer firstLine = new StringBuffer();
        firstLine.append("Please review and respond (if needed) to the following questions/comments received: "+"\n");
        firstLine.append("\n");
        StringBuffer body = new StringBuffer();
        body.append("From (required): " + form.getFrom() + "\n");
        body.append("Email ID (required): "+form.getEmail() + "\n");
        body.append("Phone: "+form.getPhone() + "\n");
        body.append("Order#: "+form.getOrder() + "\n");
        body.append("Select a Topic (required): "+subj + "\n");
        body.append("\n");
        body.append("Questions/Comments: " + form.getComment() + "\n");
        //String fromEmail = emc.getDefaultEmailAddress();
        String fromEmail = "Orderline@xpedx.com";

        firstLine.append(body.toString());

        if(ccEmail != null && ccEmail.length() > 0){
	        emc.send(toEmail, fromEmail, ccEmail,
	        		responseSubj, I18nUtil.getUtf8Str(firstLine.toString()),
	        		Constants.EMAIL_FORMAT_PLAIN_TEXT,
	        		0, null, 0, 0, "UNKNOWN");
        }else{
        	emc.send(toEmail, fromEmail, responseSubj,
            I18nUtil.getUtf8Str(firstLine.toString()),
            Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, 0);
        }

        //Confirmation email to user

        StringBuffer confirmBody = new StringBuffer();
        confirmBody.append("Dear "+form.getFrom()+","+"\n");
        confirmBody.append("\n");
        confirmBody.append("Thank you for contacting us. We will respond to your questions/comments " +
        		"promptly. Please do not respond to this message. This is an automated message and " +
        		"the originating email address is not monitored."+"\n");
        confirmBody.append("\n");
        confirmBody.append("xpedx Customer Support"+"\n");
        confirmBody.append("\n\n");
        confirmBody.append("Original Message:"+"\n");
        confirmBody.append("________________"+"\n");
        confirmBody.append(body.toString());

        String confirmSubj = "ORDERLINE SYSTEM:  "+siteName+" - Contact Us Online Confirmation";
        emc.send(form.getEmail(), fromEmail, confirmSubj,
                I18nUtil.getUtf8Str(confirmBody.toString()),
                Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, 0);

        return;
    }

    private static void excludeEmail(PropertyDataVector contactUsTopics) {
        for (int i = 0; contactUsTopics != null && i < contactUsTopics.size(); i++) {
            PropertyData item = (PropertyData) contactUsTopics.get(i);
            String value = item.getValue();
            if (!Utility.isSet(value)) {
            	value = Constants.EMPTY;
            	item.setValue(value);
            }
            int index = value.lastIndexOf(EMAIL_DELIMITER);
            if (index > 0) {
                String newValue = value.substring(0, index);
                item.setValue(newValue);
            }
        }
    }
}
